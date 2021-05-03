const express = require(`express`)
const http = require(`http`)
const app = express()
const server = http.createServer(app)

const io = require(`socket.io`)(server)

io.sockets.on(`connection`, (socket) => {
//  console.log(`Socket connected : ${socket.id}`)
  socket.on(`enter`, (data) => {
    const roomData = JSON.parse(data)
    const userName = roomData.userName
    const roomNumber = roomData.roomNumber
    
    socket.join(`${roomNumber}`)
    console.log(`[${userName}] 님이 [${roomNumber}] 방에 입장했습니다.`)

    const enterData = {
      type : `ENTER`,
      content : `${userName} 님이 입장하셨습니다.`
    }
    socket.broadcast.to(`${roomNumber}`).emit(`update`, JSON.stringify(enterData))
  })

  socket.on(`left`, (data) => {
    const roomData = JSON.parse(data)
    const userName = roomData.userName
    const roomNumber = roomData.roomNumber

    socket.leave(`${roomNumber}`)
    console.log(`[${userName}] 님이 [${roomNumber}] 방에서 퇴장했습니다.`)

    const leftData = {
      type : `LEFT`,
      content : `${userName} 님이 퇴장했습니다.`
    }
    socket.broadcast.to(`${roomNumber}`).emit(`update`, JSON.stringify(leftData))
  })

  socket.on(`newMessage`, (data) => {
    const messageData = JSON.parse(data)
    console.log(`[방번호 ${messageData.to}] ${messageData.from} : ${messageData.content}`)
    socket.broadcast.to(`${messageData.to}`).emit(`update`, JSON.stringify(messageData))
  })

  socket.on(`disconnect`, () => {
 //   console.log(`Socket disconnected : ${socket.id}`)
  })
})

server.listen(80, () => {
  console.log(`서버 실행중...`)
})