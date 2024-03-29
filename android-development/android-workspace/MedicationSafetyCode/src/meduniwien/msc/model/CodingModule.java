package meduniwien.msc.model;

import java.math.BigInteger;
import java.util.ArrayList;

import meduniwien.msc.exception.BadFormedBase64NumberException;
import meduniwien.msc.exception.BadFormedBinaryNumberException;
import meduniwien.msc.exception.VariantDoesNotMatchAnyAllowedVariantException;
import meduniwien.msc.util.Common;

/**
 * This class implements the functionality of the coding/decoding module. It is able to represent in base64 number a list of alleles combination associated to a patient's genotype
 *
 * @author Jose Antonio Mi�arro Gim�nez
 * */
public class CodingModule {
	
	/**
	 *  It obtains the corresponding 64base number from the a list of alleles.
	 *  
	 *	@param listAlleles	It consists of pair of alleles that defines the genotype of a patient.
	 *	@return		The 64base number which represents a patient's genotype based on allele definitions. 
	 * 	@throws BadFormedBinaryNumberException 
	 * */
	public static String codeListGeneticVariations(ArrayList<GeneticMarkerGroup> listGeneticMarkerGroup, ArrayList<GenotypeElement> listGenotype) throws BadFormedBinaryNumberException {
		BigInteger bi = new BigInteger("0");		
		for(GeneticMarkerGroup gmg : listGeneticMarkerGroup){
			BigInteger bi_number_marker_variations = BigInteger.valueOf(gmg.getNumberOfVariants());
			BigInteger bi_marker_position = BigInteger.ZERO;
			for(GenotypeElement ge : listGenotype){
				if(ge.getGeneticMarkerName().equalsIgnoreCase(gmg.getGeneticMarkerName())){
					bi_marker_position = BigInteger.valueOf(gmg.getPositionGeneticMarker(ge.getCriteriaSyntax()));
				}
			}
			bi = bi.multiply(bi_number_marker_variations).add(bi_marker_position);
		}
		String base64Genotype = "";
		
		base64Genotype = convertFrom2To64(bi.toString(2));
		return base64Genotype;
	}
	
	
	/**
	 * It obtains the list of pair of alleles that are related to the given 64base genotype code.
	 * 
	 * @param base64Genotype	The 64base code that represent a patient genotype based on allele definitions.
	 * @return	The list which consists of pair of alleles that defines the genotype of a patient.
	 * @throws BadFormedBase64NumberException 
	 * @throws VariantDoesNotMatchAnAllowedVariantException 
	 * */
	public static ArrayList<GenotypeElement> decodeListGenotypeVariations(ArrayList<GeneticMarkerGroup> listGeneticMarkerGroup, String base64Genotype) throws BadFormedBase64NumberException, VariantDoesNotMatchAnyAllowedVariantException{
		
		String binaryGenotype = "";
		binaryGenotype = convertFrom64To2(base64Genotype);
		BigInteger bi = new BigInteger(binaryGenotype, 2);
		ArrayList<GenotypeElement> listGenotypeVariations = new ArrayList<GenotypeElement>();
		for(int i = listGeneticMarkerGroup.size()-1;i>=0;i--){
			GeneticMarkerGroup gmg = listGeneticMarkerGroup.get(i);
			BigInteger bi_number_allele_variations = BigInteger.valueOf(gmg.getNumberOfVariants());
			BigInteger[] values = bi.divideAndRemainder(bi_number_allele_variations);
			bi = values[0];
			listGenotypeVariations.add(0,gmg.getGenotypeElement(values[1].intValue()));
			if(i==0 && !bi.equals(BigInteger.ZERO)){
				throw new BadFormedBase64NumberException("ERROR : The number " + base64Genotype +" is greater than the maximum allowed number");
			}
		}
		
		return listGenotypeVariations;
	}

	/**
	 * It transform a binary number into a base 64 number. We assume that the binary number has a multiple of six digits. In other case, we will add '0' until we obtain a multiple of 6 digits.
	 * We add the extra digits to the left side because we process the number from right to left.
	 * 
	 * @param parameter		Binary number to transform into base 64
	 * @return	The binary number in base 64.
	 * @throws	BadFormedBinaryNumberException
	 * */
	private static String convertFrom2To64(String parameter) throws BadFormedBinaryNumberException{
		int base2 = 2;
		String notBinaryString="";
		if(parameter.length()%6!=0){
			int addDigits = 6 - parameter.length()%6;
			for(int i=0;i< addDigits;i++){
				parameter="0"+parameter;
			}
		}
		for(int i=0;i<parameter.length();i+=6){
			String num=parameter.substring(i,i+6);
			Integer numero = 0;
			try{
				numero = Integer.valueOf(num, base2);
			}catch (NumberFormatException e) {
				System.err.print("\nERROR : The number " + num +" is not correctly represented in base " + base2+".\n");
				throw new BadFormedBinaryNumberException("ERROR : The number " + num +" is not in correctly represented in base " + base2);
			}
			notBinaryString+=Common.BASE_DIGITS[numero.intValue()];
		}
		return notBinaryString;	
	}
	
	/**
	 * It transform a base 64 number into a binary number.
	 * 
	 * @param parameter		Base 64 number to transform into base 2
	 * @return	The binary number which corresponds to parameter.
	 * @throws	BadFormedBase64NumberException 
	 * */
	private static String convertFrom64To2(String parameter) throws BadFormedBase64NumberException{
		char[] list = parameter.toCharArray();
		
		String binaryString = "";
		for(int i=0;i<list.length;i++){
			char c = list[i];
			
			switch(c){
			case '0':
				binaryString+="000000";
				break;
			case '1':
				binaryString+="000001";
				break;
			case '2': 
				binaryString+="000010";
				break;
			case '3': 
				binaryString+="000011";
				break;
			case '4': 
				binaryString+="000100";
				break;
			case '5': 
				binaryString+="000101";
				break;
			case '6': 
				binaryString+="000110";
				break;
			case '7': 
				binaryString+="000111";
				break;
			case '8': 
				binaryString+="001000";
				break;
			case '9': 
				binaryString+="001001";
				break;
			case 'A': 
				binaryString+="001010";
				break;
			case 'B': 
				binaryString+="001011";
				break;
			case 'C': 
				binaryString+="001100";
				break;
			case 'D': 
				binaryString+="001101";
				break;
			case 'E': 
				binaryString+="001110";
				break;
			case 'F': 
				binaryString+="001111";
				break;
			case 'G': 
				binaryString+="010000";
				break;
			case 'H': 
				binaryString+="010001";
				break;
			case 'I': 
				binaryString+="010010";
				break;
			case 'J': 
				binaryString+="010011";
				break;
			case 'K': 
				binaryString+="010100";
				break;
			case 'L': 
				binaryString+="010101";
				break;
			case 'M': 
				binaryString+="010110";
				break;
			case 'N': 
				binaryString+="010111";
				break;
			case 'O': 
				binaryString+="011000";
				break;
			case 'P': 
				binaryString+="011001";
				break;
			case 'Q': 
				binaryString+="011010";
				break;
			case 'R': 
				binaryString+="011011";
				break;
			case 'S': 
				binaryString+="011100";
				break;
			case 'T': 
				binaryString+="011101";
				break;
			case 'U': 
				binaryString+="011110";
				break;
			case 'V': 
				binaryString+="011111";
				break;
			case 'W':
				binaryString+="100000";
				break;
			case 'X':
				binaryString+="100001";
				break;
			case 'Y': 
				binaryString+="100010";
				break;
			case 'Z': 
				binaryString+="100011";
				break;
			case 'a': 
				binaryString+="100100";
				break;
			case 'b': 
				binaryString+="100101";
				break;
			case 'c': 
				binaryString+="100110";
				break;
			case 'd': 
				binaryString+="100111";
				break;
			case 'e': 
				binaryString+="101000";
				break;
			case 'f': 
				binaryString+="101001";
				break;
			case 'g': 
				binaryString+="101010";
				break;
			case 'h': 
				binaryString+="101011";
				break;
			case 'i': 
				binaryString+="101100";
				break;
			case 'j': 
				binaryString+="101101";
				break;
			case 'k': 
				binaryString+="101110";
				break;
			case 'l': 
				binaryString+="101111";
				break;
			case 'm': 
				binaryString+="110000";
				break;
			case 'n': 
				binaryString+="110001";
				break;
			case 'o': 
				binaryString+="110010";
				break;
			case 'p': 
				binaryString+="110011";
				break;
			case 'q': 
				binaryString+="110100";
				break;
			case 'r': 
				binaryString+="110101";
				break;
			case 's': 
				binaryString+="110110";
				break;
			case 't': 
				binaryString+="110111";
				break;
			case 'u': 
				binaryString+="111000";
				break;
			case 'v': 
				binaryString+="111001";
				break;
			case 'w': 
				binaryString+="111010";
				break;
			case 'x': 
				binaryString+="111011";
				break;
			case 'y': 
				binaryString+="111100";
				break;
			case 'z': 
				binaryString+="111101";
				break;
			case '-': 
				binaryString+="111110";
				break;
			case '_': 
				binaryString+="111111";
				break;
			default:
				String listChars = "";
				for(int k=0;k<Common.BASE_DIGITS.length;k++){
					if(listChars.length()>0) listChars+=", ";
					listChars+="'"+Common.BASE_DIGITS[k]+"'";
				}
				throw new BadFormedBase64NumberException("ERROR : The number " + parameter +" is not in correctly represented in base 64. Use only one of these chars: "+listChars);
			}
		}
		return binaryString;
	}
	
}
