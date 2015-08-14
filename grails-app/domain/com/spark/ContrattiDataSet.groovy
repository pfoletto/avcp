package com.spark

class ContrattiDataSet {
	Date dataPubblicazioneDataset
	Date dataUltimoAggiornamentoDataset
	Boolean validato
	String titolo
	String estratto
	String annoRiferimento
	String urlFile
	String licenza
	Authorities authority
    static constraints = {
		dataPubblicazioneDataset()
		dataUltimoAggiornamentoDataset()
		validato()
		titolo()
		estratto()
		annoRiferimento()
		urlFile()
		licenza()
		authority()
    }
}
