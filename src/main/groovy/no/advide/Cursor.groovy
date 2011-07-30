package no.advide

class Cursor {
  int x, y
  boolean lastUpdatedByCommand = false

  boolean equals(o) {
    if (this.is(o)) return true;
    if (getClass() != o.class) return false;

    Cursor cursor = (Cursor) o;

    if (x != cursor.x) return false;
    if (y != cursor.y) return false;

    return true;
  }

  int hashCode() {
    int result;
    result = x;
    result = 31 * result + y;
    return result;
  }

  public String toString ( ) {
    return "(${x},${y})";
  }
}
