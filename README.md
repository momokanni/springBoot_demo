# springBoot_demo
`本例为springBoot2.0基础demo`</br>
`后续会添加：Spring security,solr,以及对小程序的支持`

### redis安装：[安装文档](http://blog.csdn.net/unix21/article/details/9526295 "安装文档")
redis可视化操作工具：RedisDesktopManager<br/>
redis安装配置过程需要注意一点：ping不通端口，修改配置文件 bind：127.0.0.1，这个IP要改为服务器IP，注释掉不好使。

### nginx安装：[安装文档](https://www.jianshu.com/p/d5114a2a2052 "安装文档") ，[服务配置](https://www.cnblogs.com/riverdubu/p/6426852.html) 
#### 注：
>1、打开gzip压缩。</br>
>2、跨域请求</br>
>3、websockt隧道</br>
>4、nginx缓存，且个人建议缓存放置在/dev/shm/目录下，此目录下缓存文件直接保存到服务器缓存中，读写速度快，缺点：服务器重启缓存文件丢失。</br>
>5、动静分离</br>
>6、防止SQL注入、Dos攻击</br>
>7、设置单位时间相同IP请求次数</br>
>8、清除缓存purge</br>
>9、允许打开文件最大数 cat /proc/sys/fs/file-max</br>
>9、max_fails=1 fail_timeout=30s proxy_connect_timeout

#### 需要了解的Spring知识点为：SpringAOP、Spring配置方式（1.组件扫描、自动配置，2.JavaConfig Bean）、Spring缓存注解
#### websocket 采用的是H5标准,不是sockJS,[如果有Nginx负载，一定要放开webSocket代理，否则404](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=nginx%E6%89%93%E5%BC%80websocket&rsv_pq=cac71fcc000309b7&rsv_t=f8f1hYREEHDvkRnxmeLyaJ1j%2Fi6I1EA4ekn%2FpLw0jt%2BIrxRKynAKTADRwpo&rqlang=cn&rsv_enter=1&rsv_sug3=21&rsv_sug1=21&rsv_sug7=100&rsv_sug2=0&inputT=9527&rsv_sug4=10238)
#### logback 将程序错误异常发送邮箱时，163邮箱设置，密码需为"[客户端授权密码](http://mail.163.com)"
#### [微信SDK](https://github.com/Wechat-Group/weixin-java-tools/blob/master/readme.md)
#### 微信支付</br>
```<dependency> ```</br>
```  <groupId>cn.springboot</groupId> ```</br>
```  <artifactId>best-pay-sdk</artifactId> ```</br>
```  <version>1.1.0</version> ```</br>
``` </dependency> ```

#### 打包部署</br>
>1、打包成Jar：mvn clean package -Dmaven.test.skip=true</br>
>2、shell运行脚本: nohup java -jar -Dserver.port=8888 -Dspring.profiles.active=prod springboot.jar > /dev/null 2>&1 &</br>
执行命令：bash start.sh
