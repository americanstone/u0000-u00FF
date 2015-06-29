
public class Main {

    public static void main(String[] args) throws Exception {
        Base64 b = new Base64();
           /*
        https://tools.ietf.org/html/rfc4648
       BASE64("") = ""
       BASE64("f") = "Zg=="
       BASE64("fo") = "Zm8="
       BASE64("foo") = "Zm9v"
       BASE64("foob") = "Zm9vYg=="
       BASE64("fooba") = "Zm9vYmE="
       BASE64("foobar") = "Zm9vYmFy"
         */
        System.out.println("String " + "" + " encoding result: " + b.btoa("".getBytes()));
        System.out.println("String " + "f" + " encoding result: " + b.btoa("f".getBytes()));
        System.out.println("String " + "fo" + " encoding result: " + b.btoa("fo".getBytes()));
        System.out.println("String " + "foo" + " encoding result: " + b.btoa("foo".getBytes()));
        System.out.println("String " + "foob" + " encoding result: " + b.btoa("foob".getBytes()));
        System.out.println("String " + "fooba" + " encoding result: " + b.btoa("fooba".getBytes()));
        System.out.println("String " + "foobar" + " encoding result: " + b.btoa("foobar".getBytes()));

        System.out.println("String " + "" + " decoding result: " + b.atob(""));
        System.out.println("String " + "Zg==" + " decoding result: " + b.atob("Zg=="));
        System.out.println("String " + "Zm8=" + " decoding result: " + b.atob("Zm8="));
        System.out.println("String " + "Zm9v" + " decoding result: " + b.atob("Zm9v"));
        System.out.println("String " + "Zm9vYg==" + " decoding result: " + b.atob("Zm9vYg=="));
        System.out.println("String " + "Zm9vYmE=" + " decoding result: " + b.atob("Zm9vYmE="));
        System.out.println("String " + "Zm9vYmFy" + " decoding result: " + b.atob("Zm9vYmFy"));

        /*
        https://en.wikipedia.org/wiki/Base64
        The input: pleasure.   Encodes to: cGxlYXN1cmUu
        The input: leasure.    Encodes to: bGVhc3VyZS4=
        The input: easure.     Encodes to: ZWFzdXJlLg==
        The input: asure.      Encodes to:     YXN1cmUu
        The input: sure.       Encodes to:     c3VyZS4=
         */


        System.out.println("String " + "pleasure." + " encoding result: " + b.btoa("pleasure.".getBytes()));
        System.out.println("String " + "leasure." + " encoding result: " + b.btoa("leasure.".getBytes()));
        System.out.println("String " + "easure." + " encoding result: " + b.btoa("easure.".getBytes()));
        System.out.println("String " + "asure." + " encoding result: " + b.btoa("asure.".getBytes()));
        System.out.println("String " + "sure." + " encoding result: " + b.btoa("sure.".getBytes()));
    }
}
