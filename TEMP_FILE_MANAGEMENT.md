# 临时文件管理改进方案

## 问题背景

在音频转写功能中，遇到了以下临时文件管理问题：

1. **文件路径错误**：`java.nio.file.NoSuchFileException` - 系统临时目录路径不存在
2. **文件删除失败**：`java.nio.file.FileSystemException` - "另一个程序正在使用此文件，进程无法访问"

## 解决方案

### 1. 统一临时文件目录

**修改前**：使用系统临时目录（如 `C:\Users\xxx\AppData\Local\Temp`）
```java
File.createTempFile("audio_", ".tmp")  // 系统临时目录
```

**修改后**：使用项目内部目录
```java
Path tempDir = Path.of("temp", "audio");
Files.createDirectories(tempDir);
String fileName = "audio_transcription_" + System.currentTimeMillis() + "_" + 
                 Thread.currentThread().getId() + ".m4a";
Path tempFilePath = tempDir.resolve(fileName);
```

### 2. 改进的文件删除策略

实现了多层次的文件清理机制：

#### 2.1 立即删除
```java
private boolean tryDeleteFile(Path file) {
    try {
        Files.delete(file);
        return true;
    } catch (Exception e) {
        return false;
    }
}
```

#### 2.2 重试删除
```java
// 异步重试删除，递增延迟：1s, 2s, 3s
CompletableFuture.runAsync(() -> {
    for (int i = 0; i < 3; i++) {
        Thread.sleep(1000 * (i + 1));
        if (tryDeleteFile(tempFile)) {
            return;
        }
    }
    // 重试失败，标记为JVM退出时删除
    markFileForDeletion(tempFile);
});
```

#### 2.3 JVM退出时删除
```java
private void markFileForDeletion(Path file) {
    file.toFile().deleteOnExit();
}
```

### 3. 定时清理服务

创建了 `TempFileCleanupService` 来定期清理过期的临时文件：

```java
@Scheduled(fixedRate = 3600000) // 每小时执行一次
public void cleanupExpiredTempFiles() {
    // 清理24小时前的临时文件
}
```

### 4. Spring Boot配置优化

在 `application.yaml` 中配置multipart临时目录：

```yaml
spring:
  servlet:
    multipart:
      location: temp/upload
      max-file-size: 100MB
      max-request-size: 100MB
```

### 5. 版本控制配置

在 `.gitignore` 中添加临时文件目录：

```
# 临时文件目录
temp/
```

## 目录结构

```
project-root/
├── temp/
│   ├── audio/          # 音频转写临时文件
│   └── upload/         # Spring Boot上传临时文件
├── src/
└── ...
```

## 涉及的文件修改

### 核心服务类

1. **AudioTranscriptionService.java**
   - 改进 `createTempAudioFile()` 方法
   - 增强 `cleanupTempFile()` 方法
   - 添加重试和异步删除逻辑

2. **XunfeiAudioService.java**
   - 修改临时文件创建逻辑
   - 应用相同的文件清理策略

3. **TempFileCleanupService.java**（新增）
   - 定时清理过期临时文件
   - 递归清理目录结构
   - 提供手动清理接口

### 配置文件

1. **XunZhiAdminApplication.java**
   - 添加 `@EnableScheduling` 注解

2. **application.yaml**
   - 配置multipart临时目录
   - 设置文件大小限制

3. **.gitignore**
   - 忽略临时文件目录

## 优势

1. **可控性**：临时文件在项目目录内，便于管理和调试
2. **可靠性**：多层次删除策略，确保文件最终被清理
3. **性能**：异步删除不阻塞主流程
4. **维护性**：定时清理防止临时文件堆积
5. **安全性**：避免系统临时目录权限问题

## 监控和日志

系统会记录以下日志信息：

- `DEBUG`：文件创建和删除成功
- `WARN`：文件删除失败，进行重试
- `INFO`：延迟删除成功
- `ERROR`：清理过程中的异常

## 最佳实践建议

1. **及时清理**：在业务逻辑完成后立即尝试删除临时文件
2. **异常处理**：使用try-with-resources或finally块确保清理逻辑执行
3. **监控磁盘空间**：定期检查临时目录大小
4. **日志记录**：记录文件操作日志便于问题排查
5. **配置调优**：根据业务量调整清理频率和文件过期时间

## 故障排除

### 常见问题

1. **文件仍然被占用**
   - 检查是否有流未正确关闭
   - 确认异步处理是否完成
   - 查看JVM退出时删除日志

2. **临时目录权限问题**
   - 确保应用有创建目录的权限
   - 检查磁盘空间是否充足

3. **定时清理不工作**
   - 确认 `@EnableScheduling` 注解已添加
   - 检查Spring Boot版本兼容性
   - 查看定时任务执行日志

### 调试命令

```bash
# 查看临时文件目录
ls -la temp/

# 监控文件操作
lsof | grep temp

# 检查磁盘使用
du -sh temp/
```

## 未来改进方向

1. **配置化**：将清理策略参数配置化
2. **监控集成**：集成应用监控系统
3. **压缩存储**：对大文件进行压缩存储
4. **分布式支持**：支持分布式环境下的临时文件管理