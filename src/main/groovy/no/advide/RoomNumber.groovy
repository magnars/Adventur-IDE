package no.advide

class RoomNumber {
  def position
  int number

  boolean exists() {
    Adventure.current.roomExists(number)
  }
}
