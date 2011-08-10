package no.advide

class Fix {
  Integer line
  def callback

  Fix(Integer line, callback) {
    this.line = line
    this.callback = callback
  }

  void fix() {
    callback.call()
  }

  String getIcon() {
    "fix"
  }
}
