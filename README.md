### wss

1. websocket demo
2. https://docs.spring.io/spring-framework/docs/5.2.0.M1/spring-framework-reference/web.html#websocket



#### 效果

![效果](assets/WechatIMG2.png)


#### 使用GitHub Package Registry发布

1. settings 设置
   ```xml
           <activeProfiles>
               <activeProfile>github</activeProfile>
             </activeProfiles>
           
             <profiles>
               <profile>
                 <id>github</id>
                 <repositories>
                   <repository>
                     <id>central</id>
                     <url>https://repo1.maven.org/maven2</url>
                     <releases><enabled>true</enabled></releases>
                     <snapshots><enabled>true</enabled></snapshots>
                   </repository>
                   <repository>
                     <id>github</id>
                     <name>GitHub OWNER Apache Maven Packages</name>
                     <url>https://maven.pkg.github.com/lishulongVI</url>
                   </repository>
                 </repositories>
               </profile>
             </profiles>
   
           <servers>
               <server>
                 <id>github</id>
                 <username>USERNAME</username>
                 <password>TOKEN</password>
               </server>
            </servers>
   ```

2. pom.xml 
    ```xml
        <distributionManagement>
           <repository>
             <id>github</id>
             <name>GitHub OWNER Apache Maven Packages</name>
             <url>https://maven.pkg.github.com/OWNER/REPOSITORY</url>
           </repository>
        </distributionManagement>
   ```

3. 执行命令 进行上传mvn deploy -Dmaven.test.skip=true



### python ws client

```python
import asyncio
import websockets


async def hello():
    async with websockets.connect('ws://127.0.0.1:8080/wss_quick_stt_stream') as websocket:

        import datetime

        dc = datetime.datetime.now()
        # file = open('demo.wav', 'rb')
        file = open('L11-视频交互案例-2019.pdf', 'rb')
        while True:
            import datetime

            # 1M
            a = file.read(1000000)
            # a = file.read()
            if not a:
                break
            await websocket.send(a)

            print(file.tell())

            greeting = await websocket.recv()
            print(f"< {greeting}")

            print(datetime.datetime.now() - dc)


asyncio.get_event_loop().run_until_complete(hello())

```


```python

import asyncio
import websockets


async def hello():
    async with websockets.connect('ws://127.0.0.1:8080/wss_continue') as websocket:
        await websocket.send(b'hello')

        await websocket.send(b'hello')
        await websocket.send(b'hello')

        await websocket.send(b'hello')

        await websocket.send(b'END')

        print(await websocket.recv())
        print(await websocket.recv())
        print(await websocket.recv())
        print(await websocket.recv())


asyncio.get_event_loop().run_until_complete(hello())

```