package no.advide

class PageTest extends GroovyTestCase {

  def page

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  Page getPage(int number) {
    new Page(Adventure.current.getRoom(number))
  }

  void test_should_know_next_room_number_after_cursor() {
    page = getPage(3)
    assert page.document.lines == ["#1", "", "#2", "#3"]

    page.document.cursor = [x:0, y:0]
    assert page.nextRoomNumber.number == 2

    page.document.cursor = [x:0, y:1]
    assert page.nextRoomNumber.number == 2

    page.document.cursor = [x:0, y:2]
    assert page.nextRoomNumber.number == 3

    page.document.cursor = [x:0, y:3]
    assert page.nextRoomNumber == null
  }

  void test_should_know_room_number_on_cursor() {
    page = getPage(3)
    assert page.document.lines == ["#1", "", "#2", "#3"]

    page.document.cursor = [x:0, y:0]
    assert page.currentRoomNumber.number == 1

    page.document.cursor = [x:0, y:1]
    assert page.currentRoomNumber == null
  }

  void test_should_know_target_room_number_for_jumping() {
    page = getPage(3)
    assert page.document.lines == ["#1", "", "#2", "#3"]

    page.document.cursor = [x:0, y:0]
    assert page.targetRoomNumber.number == 1

    page.document.cursor = [x:0, y:1]
    assert page.targetRoomNumber.number == 2

    page.document.cursor = [x:0, y:2]
    assert page.targetRoomNumber.number == 2

    page.document.cursor = [x:0, y:3]
    assert page.targetRoomNumber.number == 3
  }

  void test_should_know_previous_room_number_before_cursor() {
    page = getPage(3)
    assert page.document.lines == ["#1", "", "#2", "#3"]

    page.document.cursor = [x:0, y:0]
    assert page.previousRoomNumber == null

    page.document.cursor = [x:0, y:1]
    assert page.previousRoomNumber.number == 1

    page.document.cursor = [x:0, y:2]
    assert page.previousRoomNumber.number == 1

    page.document.cursor = [x:0, y:3]
    assert page.previousRoomNumber.number == 2
  }

}
