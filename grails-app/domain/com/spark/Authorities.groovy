package com.spark

class Authorities {
	String nome
	String codice
    static constraints = {
		nome()
		codice()
    }
	def String toString(){
		return nome 
	}
}
