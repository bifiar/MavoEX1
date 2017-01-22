/** some general functions tthat not releated to any object
 * Created by Ofir on 1/17/2017.
 */
abstract class Utils {

    public static int countOccurrences(String haystack, char needle)
    {
        int count = 0;
        for (int i=0; i < haystack.length(); i++)
        {
            if (haystack.charAt(i) == needle)
            {
                count++;
            }
        }
        return count;
    }
}
