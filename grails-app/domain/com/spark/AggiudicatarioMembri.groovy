package com.spark

class AggiudicatarioMembri {
	String codiceFiscale
    String ragioneSociale
	String identificativoFiscaleEstero
	String ruolo
	Aggiudicatari aggiudicatari
    static constraints = {
        ragioneSociale()
		codiceFiscale()
		identificativoFiscaleEstero()
		ruolo()
    }
	
	def String toString(){
		return ragioneSociale 
	}
}
