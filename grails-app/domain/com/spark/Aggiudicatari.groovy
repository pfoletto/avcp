package com.spark

class Aggiudicatari {
    String ragioneSociale
	String codiceFiscale
	String identicativoFiscaleEstero
    static constraints = {
        ragioneSociale()
		codiceFiscale()
		identicativoFiscaleEstero()
    }
	static hasMany = [aggiudicatarioMembri: AggiudicatarioMembri]
	def String toString(){
		return ragioneSociale 
	}
}
