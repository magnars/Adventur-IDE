package no.advide

class Alternative {
  int index, number
  String text, room, requirement

  List toNewStyle() {
    def lines = [text]
    if (room) lines << roomAndRequirement
    lines
  }

  List toOldStyle_NoRequirements() {
    def lines = [text]
    if (room) lines << room.trim()
    lines
  }

  List toOldStyle_WithRequirements() {
    def lines = [text]
    if (room) lines << room.trim()
    if (requirement) lines << requirement
    lines
  }

  String getRoomAndRequirement() {
    if (hasRequirement()) "${room.trim()} ? $requirement"
    else room.trim()
  }

  boolean hasRequirement() {
    requirement && requirement != "-"
  }
}
