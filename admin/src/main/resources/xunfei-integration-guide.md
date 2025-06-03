# 讯飞AI功能集成指南

本指南介绍如何在Spring项目中集成和使用讯飞AI功能，包括音频转文字和人脸识别。

## 1. 配置文件设置

在 `application.yml` 中添加讯飞配置：

```yaml
# 讯飞AI配置
xunfei:
  # 基础配置
  app-id: "your_app_id"          # 讯飞应用ID
  api-key: "your_api_key"        # 讯飞API Key
  api-secret: "your_api_secret"  # 讯飞API Secret
  rta-api-key: "your_rta_key"    # 实时语音转写API Key
  
  # 语音听写配置
  iat:
    dwa: "wpgs"                   # 动态修正功能
    language: "zh_cn"             # 语言类型
    domain: "iat"                 # 应用领域
    audio-encoding: "raw"         # 音频编码
    sample-rate: "16000"          # 音频采样率
  
  # 实时语音转写配置
  rtasr:
    language: "zh_cn"             # 语言类型
    domain: "iat"                 # 应用领域
    audio-encoding: "raw"         # 音频编码
    sample-rate: "16000"          # 音频采样率
    punctuation: true             # 是否开启标点符号添加
  
  # 人脸识别配置
  face:
    compare-threshold: 0.7        # 人脸对比阈值
    max-image-size: 5242880       # 图片最大尺寸（5MB）
    supported-formats:            # 支持的图片格式
      - jpg
      - jpeg
      - png
      - bmp
```

## 2. 已创建的工具类

### 2.1 XunfeiAudioService - 音频转文字服务

**功能：**
- 音频文件转文字（语音听写）
- 实时音频流转文字

**主要方法：**
```java
// 音频文件转文字
CompletableFuture<String> audioToText(InputStream audioInputStream)

// 实时音频流转文字
CompletableFuture<String> realTimeAudioToText(InputStream audioInputStream, AudioResultCallback callback)
```

### 2.2 XunfeiFaceService - 人脸识别服务

**功能：**
- 人脸对比
- 人脸属性识别（年龄、性别、表情、颜值）
- 综合人脸属性识别

**主要方法：**
```java
// 人脸对比
CompletableFuture<FaceCompareResult> compareFaces(MultipartFile image1, MultipartFile image2)

// 单个属性识别
CompletableFuture<FaceAttributeResult> detectAge(MultipartFile image)
CompletableFuture<FaceAttributeResult> detectGender(MultipartFile image)
CompletableFuture<FaceAttributeResult> detectExpression(MultipartFile image)
CompletableFuture<FaceAttributeResult> detectBeauty(MultipartFile image)

// 综合属性识别
CompletableFuture<ComprehensiveFaceResult> detectAllAttributes(MultipartFile image)
```

### 2.3 XunfeiController - REST API控制器

**提供的API接口：**

#### 音频转文字接口
- `POST /api/xunfei/audio/convert` - 音频文件转文字
- `POST /api/xunfei/audio/realtime` - 实时音频转文字

#### 人脸识别接口
- `POST /api/xunfei/face/compare` - 人脸对比
- `POST /api/xunfei/face/age` - 年龄识别
- `POST /api/xunfei/face/gender` - 性别识别
- `POST /api/xunfei/face/expression` - 表情识别
- `POST /api/xunfei/face/beauty` - 颜值识别
- `POST /api/xunfei/face/attributes` - 综合属性识别

#### 健康检查接口
- `GET /api/xunfei/health` - 服务健康检查

## 3. 前端调用示例

### 3.1 音频转文字

```javascript
// 音频文件转文字
const convertAudio = async (audioFile) => {
    const formData = new FormData();
    formData.append('file', audioFile);
    
    try {
        const response = await fetch('/api/xunfei/audio/convert', {
            method: 'POST',
            body: formData
        });
        
        const result = await response.json();
        if (result.success) {
            console.log('转换结果:', result.text);
            return result.text;
        } else {
            console.error('转换失败:', result.message);
        }
    } catch (error) {
        console.error('请求失败:', error);
    }
};

// 实时音频转文字
const realtimeAudioConvert = async (audioFile) => {
    const formData = new FormData();
    formData.append('file', audioFile);
    
    try {
        const response = await fetch('/api/xunfei/audio/realtime', {
            method: 'POST',
            body: formData
        });
        
        const result = await response.json();
        if (result.success) {
            console.log('实时转换结果:', result.text);
            return result.text;
        }
    } catch (error) {
        console.error('实时转换失败:', error);
    }
};
```

### 3.2 人脸识别

```javascript
// 人脸对比
const compareFaces = async (image1, image2) => {
    const formData = new FormData();
    formData.append('image1', image1);
    formData.append('image2', image2);
    
    try {
        const response = await fetch('/api/xunfei/face/compare', {
            method: 'POST',
            body: formData
        });
        
        const result = await response.json();
        if (result.success) {
            console.log('相似度:', result.similarity);
            return result;
        }
    } catch (error) {
        console.error('人脸对比失败:', error);
    }
};

// 综合人脸属性识别
const detectFaceAttributes = async (imageFile) => {
    const formData = new FormData();
    formData.append('image', imageFile);
    
    try {
        const response = await fetch('/api/xunfei/face/attributes', {
            method: 'POST',
            body: formData
        });
        
        const result = await response.json();
        if (result.success) {
            console.log('人脸属性:', {
                age: result.age,
                gender: result.gender,
                expression: result.expression,
                beauty: result.beauty
            });
            return result;
        }
    } catch (error) {
        console.error('人脸属性识别失败:', error);
    }
};
```

### 3.3 摄像头录像处理示例

```javascript
// 处理摄像头录像数据
const processCameraVideo = async () => {
    try {
        // 获取摄像头流
        const stream = await navigator.mediaDevices.getUserMedia({ 
            video: true, 
            audio: true 
        });
        
        // 创建MediaRecorder录制音视频
        const mediaRecorder = new MediaRecorder(stream);
        const chunks = [];
        
        mediaRecorder.ondataavailable = (event) => {
            if (event.data.size > 0) {
                chunks.push(event.data);
            }
        };
        
        mediaRecorder.onstop = async () => {
            // 处理录制完成的数据
            const blob = new Blob(chunks, { type: 'video/webm' });
            
            // 如果需要提取音频进行转文字
            const audioBlob = await extractAudio(blob);
            const audioFile = new File([audioBlob], 'audio.wav', { type: 'audio/wav' });
            
            // 调用音频转文字接口
            const text = await convertAudio(audioFile);
            console.log('录像中的语音转文字结果:', text);
            
            // 如果需要进行人脸识别，可以从视频中提取帧
            const imageBlob = await extractFrame(blob);
            const imageFile = new File([imageBlob], 'frame.jpg', { type: 'image/jpeg' });
            
            // 调用人脸属性识别接口
            const faceResult = await detectFaceAttributes(imageFile);
            console.log('人脸识别结果:', faceResult);
        };
        
        // 开始录制
        mediaRecorder.start();
        
        // 5秒后停止录制
        setTimeout(() => {
            mediaRecorder.stop();
            stream.getTracks().forEach(track => track.stop());
        }, 5000);
        
    } catch (error) {
        console.error('摄像头访问失败:', error);
    }
};

// 从视频中提取音频的辅助函数
const extractAudio = async (videoBlob) => {
    // 这里需要使用Web Audio API或其他库来提取音频
    // 具体实现取决于你的需求
    return videoBlob; // 简化示例
};

// 从视频中提取帧的辅助函数
const extractFrame = async (videoBlob) => {
    return new Promise((resolve) => {
        const video = document.createElement('video');
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        
        video.onloadedmetadata = () => {
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            ctx.drawImage(video, 0, 0);
            
            canvas.toBlob(resolve, 'image/jpeg', 0.8);
        };
        
        video.src = URL.createObjectURL(videoBlob);
    });
};
```

## 4. 使用注意事项

1. **配置讯飞账号信息**：确保在配置文件中正确设置讯飞的AppID、APIKey等信息

2. **文件大小限制**：
   - 音频文件建议不超过10MB
   - 图片文件建议不超过5MB

3. **支持的格式**：
   - 音频：wav, mp3, pcm等
   - 图片：jpg, jpeg, png, bmp

4. **异步处理**：所有API都是异步的，使用CompletableFuture处理结果

5. **错误处理**：注意捕获和处理可能的异常，如网络错误、格式不支持等

6. **性能优化**：
   - 对于大文件，考虑分片处理
   - 实时音频转写适合流式数据处理
   - 人脸识别可以考虑缓存结果

## 5. 扩展功能

如需要其他讯飞AI功能，可以参考现有代码结构进行扩展：

1. 在对应的Service类中添加新方法
2. 在Controller中添加新的API接口
3. 更新配置类以支持新功能的参数
4. 编写相应的前端调用代码

## 6. 故障排查

1. **配置问题**：检查application.yml中的讯飞配置是否正确
2. **网络问题**：确保服务器能够访问讯飞API
3. **格式问题**：确保上传的文件格式被支持
4. **权限问题**：检查讯飞账号的API调用权限和余额
5. **日志查看**：查看应用日志获取详细错误信息