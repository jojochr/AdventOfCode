namespace Day1 {
    public static class SortMethods {
        public static int[] BubbleSortThis(this int[] array) {
            if(array.Length <= 1) {
                return array;
            }

            bool finished = false;

            while(finished == false) {
                bool hasSwapped = false;

                // Always start at 0
                for(int i = 0; i <= array.Length - 2; i++) {
                    if(array[i] > array[i + 1]) {
                        (array[i], array[i + 1]) = (array[i + 1], array[i]);
                        hasSwapped = true;
                    }
                }

                if(hasSwapped == false) {
                    finished = true;
                }
            }

            return array;
        }
    }
}
