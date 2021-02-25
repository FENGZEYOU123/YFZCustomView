package utils;

public interface OnInputListener {
   void inputNumber(int number);
   void inputLetter(String str, boolean isSingleLetter);
   void isDeleteClick(boolean isClick);
   void isBackClick(boolean isClick);
}
