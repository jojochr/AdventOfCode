using System.Collections.Immutable;

namespace Day2 {
    public static class StatusCodeChecker {
        /// <summary>This Method checks if the provided StatusCodes are valid</summary>
        /// <param name="statusValues">The StatusValues, the status code consists of</param>
        /// <param name="minDifference">The minimal difference between different StatusValues</param>
        /// <param name="maxDifference">The maximal difference between different StatusValues</param>
        /// <returns>True -> If the StatusCode is valid<br/>False -> If the StatusCode is invalid</returns>
        public static bool CheckStatusCode(ImmutableArray<ushort> statusValues, in ushort minDifference, in ushort maxDifference) {
            if(statusValues.Length <= 1) {
                return false;
            }

            ushort currentStatusCode = statusValues[0];
            ushort nextStatusCode = statusValues[1];

            bool codesShouldBeAscending;
            if(currentStatusCode < nextStatusCode) {
                codesShouldBeAscending = true;
            } else if(currentStatusCode > nextStatusCode) {
                codesShouldBeAscending = false;
            } else {
                return false; // Return false if the current StatusCode is the same as the next
            }

            return CheckCodesRecursively(statusValues, startingIndex: 0, minDifference, maxDifference, codesShouldBeAscending);
        }

        /// <summary>Checks the StatusValues recursively</summary>
        /// <param name="statusValues">The StatusValues to check</param>
        /// <param name="startingIndex">The current position that should be checked</param>
        /// <param name="minDifference">The minimal difference between different StatusValues</param>
        /// <param name="maxDifference">The maximal difference between different StatusValues</param>
        /// <param name="shouldBeAscending">Indicates if the values should get higher, after every iteration, or lower</param>
        /// <returns>True -> If the StatusCode is valid<br/>False -> If the StatusCode is invalid</returns>
        private static bool CheckCodesRecursively(in ImmutableArray<ushort> statusValues, in int startingIndex, in ushort minDifference, in ushort maxDifference, in bool shouldBeAscending) {
            if(statusValues.Length <= 1) {
                return false;
            }

            if(startingIndex == statusValues.Length - 1) {
                return true; // This is the happy case where we made it till the end without one bad Check :)
            }

            ushort currentStatusCode = statusValues[startingIndex];
            ushort nextStatusCode = statusValues[startingIndex + 1];
            if(Math.Abs(currentStatusCode - nextStatusCode) < minDifference || // If the difference between StatusCodes is smaller than we defined
               Math.Abs(currentStatusCode - nextStatusCode) > maxDifference) /* If the difference between StatusCodes is bigger than we defined */ {
                return false;
            } else if((currentStatusCode > nextStatusCode && shouldBeAscending == true) || // If we should be ascending but are currently not
                      (currentStatusCode < nextStatusCode && shouldBeAscending == false)) /* If we should be descending but are currently not */ {
                return false;
            } else {
                return CheckCodesRecursively(statusValues, startingIndex + 1, minDifference, maxDifference, shouldBeAscending);
            }
        }
    }
}
