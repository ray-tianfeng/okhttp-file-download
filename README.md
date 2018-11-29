## 基于OKHTTP的文件下载  ##

- **多线程下载**
- **断点续传**
- **多任务下载**
- **任务保活**</br>
	最开始用okhttp实现了一个文件下载的功能，但是多次测试后发现任务下载是会出现流异常断掉，和http协议问题，导致下载异常暂停。</br>
### 主要构成 ###
1. DownLoadConfig</br>
下载配置：url、文件储存位置、回调、分片（有多少个分片就用多少个线程下载），文件md5等

2. DownLoader</br>
文件下载对象：得到下载配置后，先去加载本地缓存，如果加载成功使用缓存下载，未发现配置则执行下载，这个类主要作用去实现下载，缓存和恢复下载进度，同步计算下载进度，和任务保活。但是他不会主动同步需要管理通知同步或者检查任务。

3. FileDownLoadManager</br>
下载对象管理:创建对象下载，通知对象同步下载进度、缓存任务进度。这是一个总的调度对象，有所有下载对象的引用。
### 用法 ###


-  LIB配置</br>
<pre><code>allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.github.ray-tianfeng:FileDownload:v1.0'
}</pre></code>

- 初始化</br>
<pre><code>FileDownLoadManager.getInstance().init(this);  
@Override
protected void onResume() {
    super.onResume();
    FileDownLoadManager.getInstance().onResume();
}
@Override
protected void onStop() {
    super.onStop();
    FileDownLoadManager.getInstance().onPause();
}
</code></pre>

- 调用</br>
<pre><code>DownLoadConfig config = new DownLoadConfig();
config.setCallback(callback);//设置回调
config.setBurstCount(burstCount);//设置分片大小，也就是使用多少个线程下载
config.setDownloadUrl(url);//文件url
config.setMd5(md5);//文件md5，设置后如果md5值和下载文件不同将提示下载失败
config.setSavePath(savePath);//文件保存全路径
DownLoader downLoader = FileDownLoadManager.getInstance().startDownload(config);
</code></pre>
git源码传送门：https://github.com/ray-tianfeng/FileDownload.git
    

