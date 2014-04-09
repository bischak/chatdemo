Simple Chat demo
========

###   Extremely simple demo of chat engine based on Scala/Play!

Run demo chat:

    play run

Open channel "mychat" via WebSocket client:

    curl -i -N -H "Connection: Upgrade" -H "Upgrade: websocket" -H "Host: localhost:9000" -H "Origin: http://localhost:9000" http://localhost:9000/channel/get/mychat

Post message "test" to channel "mychat" by user "person":

    curl -XPOST --data "user=person&message=test" http://localhost:9000/channel/send/mychat
