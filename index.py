import asyncio
import websockets


async def hello():
    async with websockets.connect('ws://127.0.0.1:8080/wss_b') as websocket:

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
