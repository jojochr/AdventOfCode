namespace Shared {
    public static class SortMethods {
        /// <summary>
        /// Extension method for <see cref="int"/>-arrays that executes a bubble sort on the array<br/>
        /// This mutates the Array in memory and additionally returns the array for better method chaining
        /// </summary>
        /// <param name="array">The Array to sorted by in memory mutation</param>
        /// <returns>The same reference to the sorted parameter: <paramref name="array"/></returns>
        public static int[] BubbleSortThis(this int[] array) {
            if(array.Length <= 1) {
                return array;
            }

            bool finished = false;

            while(finished == false) {
                bool hasSwapped = false;
                // Always start at 0
                for(int i = 0; i <= array.Length - 2; i++) {
                    // Skip if we don't need to swap
                    if(array[i] <= array[i + 1]) {
                        continue;
                    }

                    (array[i], array[i + 1]) = (array[i + 1], array[i]);
                    hasSwapped = true; // Never forget this step :D
                }

                if(hasSwapped == false) {
                    finished = true;
                }
            }

            return array;
        }
    }
}
