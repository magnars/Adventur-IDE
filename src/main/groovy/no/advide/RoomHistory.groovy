package no.advide

class RoomHistory {
  List<Room> history

  RoomHistory(Room startingRoom) {
    history = [startingRoom]
  }

  void push(int number) {
    history << Adventure.current.getRoom(number)
  }

  void push(Room room) {
    history << room
  }

  Room getCurrent() {
    history.last()
  }

  Room pop() {
    history.pop()
  }

  boolean empty() {
    history.size() == 0
  }
}
