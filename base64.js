/**
 * base64 encoding & decoding
 * for fixing browsers which don't support Base64 | btoa |atob
 * http://www.cnblogs.com/hongru/archive/2012/01/14/2321397.html
 */
/*

1.group characters with 3*8bit(24-bit block)  
for instance: Tom|Sha|non
2.get ascii code for each character
3.convert ascii code to 8bit binary(total 3group * 8bit = 24bit)
4.group binary by 6bit, (total 4group * 6bit = 24bit)
specific case: 
When the number of bytes to encode is not divisible by three (that is, if there are only one or two bytes of input for the last 24-bit block), then the following action is performed:
Add extra bytes with value zero so there are three bytes, and perform the conversion to base64. If there was only one significant input byte, only the first two base64 digits are picked (12 bits), and if there were two significant input bytes, the first three base64 digits are picked (18 bits). '=' characters might be added to make the last block contain four base64 characters.
As a result, when the last group contains one octet, the four least significant bits of the final 6-bit block are set to zero; and when the last group contains two octets, the two least significant bits of the final 6-bit block are set to zero.
 */
(function (win, undefined) {
 
     var Base64 = function () {
        var base64hash = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
        
        // btoa method
        function _btoa (s) {
            //0-255 ascii code is valid
            if (/([^\u0000-\u00ff])/.test(s)) {
                throw new Error('INVALID_CHARACTER_ERR');
            }    
            var i = 0,
                prev,
                ascii,
                mod,
                result = [];

            while (i < s.length) {
                ascii = s.charCodeAt(i);
                mod = i % 3;

                switch(mod) {
                    case 0:
                    //get first 6bit by moving first 8bit block 2bit to right
                        result.push(base64hash.charAt(ascii >> 2));
                        break;
                    case 1:
                        //2nd 6bit = the last 2bit of first 8bit block + the top 4bit of second 8bit block 
                        result.push(base64hash.charAt((prev & 3) << 4 | (ascii >> 4)));
                        break;
                    //0x0f = 00001111  0x3f = 00111111
                    //3rd 6bit = the last 4bit of second 8bit block + the top 2bit of 3rd 8bit block
                    //fourth 6bit = the last 6bit of 3rd 8bit block
                    case 2:
                        result.push(base64hash.charAt((prev & 0x0f) << 2 | (ascii >> 6)));
                        result.push(base64hash.charAt(ascii & 0x3f));
                        break;
                }

                prev = ascii;
                i ++;
            }
            // 循环结束后看mod, 为0 证明需补3个6位，第一个为最后一个8位的最后两位后面补4个0。另外两个6位对应的是异常的“=”；
            // mod为1，证明还需补两个6位，一个是最后一个8位的后4位补两个0，另一个对应异常的“=”
            // mod == 0 meaning after grouping 3*8bit, only 1 character(8bits) left, so we need to add 3*6bits to end up with 3*8bits
            //The last 2 bit of the last 8bits block + 0000 
            if(mod == 0) {
                result.push(base64hash.charAt((prev & 3) << 4));
                result.push('==');
            } else if (mod == 1) {
                result.push(base64hash.charAt((prev & 0x0f) << 2));
                result.push('=');
            }

            return result.join('');
        }

        // atob method
        function _atob (s) {
            //remove space and '=' cuz we add = which is not existing in original characters
            s = s.replace(/\s|=/g, '');
            var cur,
                prev,
                mod,
                i = 0,
                result = [];

            while (i < s.length) {
                cur = base64hash.indexOf(s.charAt(i));
                mod = i % 4;

                switch (mod) {
                    case 0:
                        //TODO
                        break;
                    case 1:
                        result.push(String.fromCharCode(prev << 2 | cur >> 4));
                        break;
                    case 2:
                        result.push(String.fromCharCode((prev & 0x0f) << 4 | cur >> 2));
                        break;
                    case 3:
                        result.push(String.fromCharCode((prev & 3) << 6 | cur));
                        break;
                        
                }

                prev = cur;
                i ++;
            }

            return result.join('');
        }

        return {
            btoa: _btoa,
            atob: _atob,
            encode: _btoa,
            decode: _atob
        };
    }();

    if (!win.Base64) { win.Base64 = Base64 }
    if (!win.btoa) { win.btoa = Base64.btoa }
    if (!win.atob) { win.atob = Base64.atob }

 })(window)