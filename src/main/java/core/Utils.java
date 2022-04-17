package core;

public enum Utils implements IUtils {
    WHITE() {
        @Override
        public boolean isYellow() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK () {
        @Override
        public boolean isYellow() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };
}
