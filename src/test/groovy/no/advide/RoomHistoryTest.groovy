package no.advide

class RoomHistoryTest extends GroovyTestCase {

  RoomHistory history

  void setUp() {
    AdventureTest.setUpCurrent()
    history = new RoomHistory(Adventure.current.getRoom(0))
  }

  void test_should_have_starting_room() {
    assert history.current == Adventure.current.getRoom(0)
  }

  void test_should_push_rooms() {
    history.push(Adventure.current.getRoom(1))
    assert history.current == Adventure.current.getRoom(1)
  }

  void test_should_push_room_numbers() {
    history.push(2)
    assert history.current == Adventure.current.getRoom(2)
  }

  void test_should_pop_history() {
    history.push(2)
    assert history.pop().number == 2
    assert history.current.number == 0
  }

  void test_should_know_if_history_is_empty() {
    assert !history.empty()
    history.pop()
    assert history.empty()
  }

}
