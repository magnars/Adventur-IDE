package no.advide

class CursorTest extends GroovyTestCase {

  void test_should_move_right_within_boundaries() {
    def cursor = new Cursor([" "], 0, 0)
    cursor.right()
    assert cursor.at(x:1, y:0)
    cursor.right()
    assert cursor.at(x:1, y:0)
  }

  void test_should_wrap_when_moving_right() {
    def cursor = new Cursor([" ", ""], 0, 0)
    cursor.right()
    cursor.right()
    assert cursor.at(x:0, y:1)
  }

  void test_should_move_left_within_boundaries() {
    def cursor = new Cursor([" "], 0, 0)
    cursor.right()
    cursor.left()
    assert cursor.at(x:0, y:0)
    cursor.left()
    assert cursor.at(x:0, y:0)
  }

  void test_should_wrap_when_moving_left() {
    def cursor = new Cursor([" ", ""], 0, 0)
    cursor.down()
    cursor.left()
    assert cursor.at(x:1, y:0)
  }

  void test_should_move_down_within_boundaries() {
    def cursor = new Cursor(["", ""], 0, 0)
    cursor.down()
    assert cursor.at(x:0, y:1)
    cursor.down()
    assert cursor.at(x:0, y:1)
  }

  void test_should_wrap_when_moving_down() {
    def cursor = new Cursor(["", " "], 0, 0)
    cursor.down()
    cursor.down()
    assert cursor.at(x:1, y:1)
  }

  void test_should_move_up_within_boundaries() {
    def cursor = new Cursor(["", ""], 0, 0)
    cursor.down()
    cursor.up()
    assert cursor.at(x:0, y:0)
    cursor.up()
    assert cursor.at(x:0, y:0)
  }

  void test_should_wrap_when_moving_up() {
    def cursor = new Cursor([" "], 0, 0)
    cursor.right()
    cursor.up()
    assert cursor.at(x:0, y:0)
  }

  void test_should_adjust_y_if_somehow_outside() {
    def cursor = new Cursor(["", ""], 0, 0)
    cursor._y = 99
    assert cursor.at(x:0, y:1)
    cursor.up()
    assert cursor.at(x:0, y:0)
  }

  void test_should_account_for_adjusted_y_if_moved_while_somehow_outside() {
    def cursor = new Cursor(["", ""], 0, 0)
    cursor._y = 99
    cursor.up()
    assert cursor.at(x:0, y:0)
  }

  void test_should_move_to_dummy_x_if_outside() {
    def cursor = new Cursor(["long line", "short", "long line"], 0, 0)
    cursor._x = 9
    cursor.down()
    assert cursor.at(x:5, y:1)
    cursor.down()
    assert cursor.at(x:9, y:2)
  }

  void test_should_accept_dummy_x_value_if_moved_horizontally() {
    def cursor = new Cursor(["long line", "short", "long line"], 0, 0)
    cursor._x = 9
    cursor.down()
    cursor.left()
    assert cursor.at(x:4, y:1)
  }

  void test_should_move_all_right() {
    def cursor = new Cursor(["abc"], 0, 0)
    cursor.allRight()
    assert cursor.at(x:3, y:0)
  }

  void test_should_move_all_left() {
    def cursor = new Cursor(["abc"], 0, 0)
    cursor.allRight()
    cursor.allLeft()
    assert cursor.at(x:0, y:0)
  }

  void test_should_move_all_down() {
    def cursor = new Cursor(["", "", ""], 0, 0)
    cursor.allDown()
    assert cursor.at(x:0, y:2)
  }

  void test_should_move_all_up() {
    def cursor = new Cursor(["", "", ""], 0, 0)
    cursor.allDown()
    cursor.allUp()
    assert cursor.at(x:0, y:0)
  }

  void test_should_move_scrollTop_down_if_below_height_and_stay() {
    def cursor = new Cursor(["", "", "", "", ""], 0, 5, 0)
    assert cursor.calculateScrollTop(3) == 2
    cursor.up()
    assert cursor.calculateScrollTop(3) == 2
  }

  void test_should_move_scrollTop_up_if_under_cursor() {
    def cursor = new Cursor(["", "", "", "", ""], 0, 0, 2)
    assert cursor.calculateScrollTop(3) == 0
    cursor.down()
    assert cursor.calculateScrollTop(3) == 0
  }

  void test_should_move_scrollTop_down_so_line_under_cursor_shows() {
    def cursor = new Cursor(["", "", "", "", ""], 0, 3, 0)
    assert cursor.calculateScrollTop(3) == 1
  }

  void test_should_move_scrollTop_up_so_line_over_cursor_shows() {
    def cursor = new Cursor(["", "", "", "", ""], 0, 2, 2)
    assert cursor.calculateScrollTop(3) == 1
  }

}
