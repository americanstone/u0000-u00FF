
public class Base64 {

	private static String codes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	//encode binary to ascii. from large set to small set is encode
	public String btoa(byte[] in){

		int i = 0;
		int ascii = '\u0000';
		int previous = '\u0000';
		int mod = -1;
		StringBuilder sb = new StringBuilder();

		while( i < in.length){
			ascii = in[i];
			mod = i % 3;
			switch(mod){
				case 0:
					sb.append(codes.charAt(ascii >> 2));
					break;
				case 1:
					sb.append(codes.charAt((previous & 0x03) << 4| ascii >> 4) );
					break;
				case 2:
					sb.append(codes.charAt(((previous & 0xf) <<2) | (ascii >> 6)));
					sb.append(codes.charAt(ascii & 0x3f));
					break;
			}
		 previous = ascii;
		 i++;
		}

		if(mod == 0){
		   sb.append(codes.charAt((previous & 0x03) << 4));
			sb.append('=').append('=');
		}
		if(mod == 1){
		  sb.append(codes.charAt((previous & 0xf) <<2 ));
			sb.append('=');
		}

		return sb.toString();
	}

	//decode ascii to binary. from small set to large set is decode
	public String atob(String o){
		if(o.length() % 4 != 0){
			System.err.println("Invalid base64 input");
			return null;
		}
		o = o.replace("=","");
		StringBuilder sb = new StringBuilder();
		int i = 0;
		int curr = 0;
		int mod = 0;
		int previous = 0;
		while(i < o.length()){
			curr = codes.indexOf(o.charAt(i));
			mod = i % 4;
			switch(mod){
				case 0:
					break;
				case 1:

					sb.append(Character.toChars(previous << 2 | curr >> 4));
					break;
				case 2:
					sb.append(Character.toChars((previous & 0x0f) << 4 | curr >> 2));
					break;
				case 3:
					sb.append(Character.toChars((previous & 0x03) << 6 | curr));
					break;
			}
			previous = curr;
			i++;
		}

		return sb.toString();
	}


	//https://en.wikipedia.org/wiki/Base64
	private static byte[] base64Decode(String input)    {
		if (input.length() % 4 != 0)    {
			System.err.println("Invalid base64 input");
			return null;
		}
		byte decoded[] = new byte[((input.length() * 3) / 4) - (input.indexOf('=') > 0 ? (input.length() - input.indexOf('=')) : 0)];
		char[] inChars = input.toCharArray();
		int j = 0;
		int b[] = new int[4];
		for (int i = 0; i < inChars.length; i += 4)     {
			// This could be made faster (but more complicated) by precomputing these index locations
			b[0] = codes.indexOf(inChars[i]);
			b[1] = codes.indexOf(inChars[i + 1]);
			b[2] = codes.indexOf(inChars[i + 2]);
			b[3] = codes.indexOf(inChars[i + 3]);
			decoded[j++] = (byte) ((b[0] << 2) | (b[1] >> 4));
			if (b[2] < 64)      {
				decoded[j++] = (byte) ((b[1] << 4) | (b[2] >> 2));
				if (b[3] < 64)  {
					decoded[j++] = (byte) ((b[2] << 6) | b[3]);
				}
			}
		}

		return decoded;
	}

	private static String base64Encode(byte[] in)       {
		StringBuffer out = new StringBuffer((in.length * 4) / 3);
		int b;
		for (int i = 0; i < in.length; i += 3)  {
			b = (in[i] & 0xFC) >> 2;
			out.append(codes.charAt(b));
			b = (in[i] & 0x03) << 4;
			if (i + 1 < in.length)      {
				b |= (in[i + 1] & 0xF0) >> 4;
				out.append(codes.charAt(b));
				b = (in[i + 1] & 0x0F) << 2;
				if (i + 2 < in.length)  {
					b |= (in[i + 2] & 0xC0) >> 6;
					out.append(codes.charAt(b));
					b = in[i + 2] & 0x3F;
					out.append(codes.charAt(b));
				} else  {
					out.append(codes.charAt(b));
					out.append('=');
				}
			} else      {
				out.append(codes.charAt(b));
				out.append("==");
			}
		}

		return out.toString();
	}
}
