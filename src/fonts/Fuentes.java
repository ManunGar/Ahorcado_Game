package fonts;

import java.awt.Font;
import java.io.InputStream;

public class Fuentes {
    private Font font = null;
    public String BIT8 = "8-BIT WONDER.TTF";
    public String PC = "PixCon.ttf";
    public String PLL = "Pixellari.ttf";

    /* Font.PLAIN = 0 , Font.BOLD = 1 , Font.ITALIC = 2
 * 	   tamaño = float
 */
    public Font fuente( String fontName, int estilo, float tamanio)
    {
         try {
            //Se carga la fuente
            InputStream is =  getClass().getResourceAsStream(fontName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            //Si existe un error se carga fuente por defecto ARIAL
            System.err.println(fontName + " No se cargo la fuente");
            font = new Font("Arial", Font.PLAIN, 14);            
        }
        Font tfont = font.deriveFont(estilo, tamanio);
        return tfont;
    }
}
