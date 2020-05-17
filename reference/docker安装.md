# docker安装与部署

## docker安装

这里是windows10家庭普通版

首先下载DockerToolbox, 建议下载最新版, 但是国外镜像访问不到, 所以需要想办法搞到国内镜像.

双击安装.

打开Docker QuickStart Terminal, 还是由于国内网络限制的原因, 导致报错.

直接到安装目录(例如:C:\Program Files\Docker Toolbox), 将里面的boot2docker.iso复制到C:\Users\用户名\.docker\machine\cache目录中

然后再次打开Docker QuickStart Terminal.

应该不会报错了. (可能需要断网打开)

至此docker安装成功!

## docker镜像仓库配置

由于国外镜像仓库无法访问, 这里使用阿里云的镜像加速.

首先登录阿里云, 找到容器镜像服务-镜像加速器, 获取加速器地址:https://xxxxxxxx.mirror.aliyuncs.com


**配置默认镜像路径**

在cmd窗口, 执行:
```
start %DOCKER_CERT_PATH%
```

打开docker目录, 找到config.json文件, 修改其中的

```
"RegistryMirror": [
                "https://xxxxxxxx.mirror.aliyuncs.com"
            ],
```

**修改当前镜像仓库路径**

进入Docker QuickStart Terminal

先执行docker-machine ssh [machine-name]进入VM bash

```sh
>>> docker-machine ssh default
```

执行:
```
sudo vi /var/lib/boot2docker/profile
```

修改镜像配置.

在–label provider=virtualbox的下一行添加
--registry-mirror https://xxxxxxxx.mirror.aliyuncs.com

然后执行:

```
sudo /etc/init.d/docker restart
```

执行:

```
exit
```
离开VM bash

执行:

```
docker-machine restart default
```

重启docker-machine.

使用:

```
docker-machine inspect default
```

和

```
docker info
```

检查是否修复完成.