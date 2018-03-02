package com.algaworks.erp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.model.TipoEmpresa;
import com.algaworks.erp.repository.Empresas;
import com.algaworks.erp.service.CadastroEmpresaService;
import com.algaworks.erp.util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Empresas empresas;
	@Inject
	private CadastroEmpresaService cadastroEmpresa;
	@Inject
	private FacesMessages messages;

	private Empresa empresaEdicao = new Empresa();
	private Empresa empresaSelecionada;
	private List<Empresa> todasEmpresas;

	/* GETTERS AND SETTER */
	public List<Empresa> getTodasEmpresas() {
		return todasEmpresas;
	}
	
	public TipoEmpresa[] getTipoEmpresas(){
		return TipoEmpresa.values();
	}
	
	public Empresa getEmpresaEdicao() {
		return empresaEdicao;
	}
	public void setEmpresaEdicao(Empresa empresaEdicao) {
		this.empresaEdicao = empresaEdicao;
	}
	
	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}
	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}	

	/* MÉTODOS */
	public void preparar(){
		empresaEdicao = new Empresa();
	}
	public void consultar() {
		todasEmpresas = empresas.todas();
	}

	public void excluir(){
		cadastroEmpresa.excluir(empresaSelecionada);
		empresaSelecionada = null;
		consultar();
		
		messages.info("Empresa excluída com sucesso!");
		
		/*se a instacias for excluida com sucesso ele dá um update nos ids informados abaixo
		 * O correto era colocar na tag no html o atributo 
		 * (update="empresas-table msgs toolbar")*/
		RequestContext.getCurrentInstance().update(
				Arrays.asList("frm:msgs", "frm:empresas-table", "frm:toolbar"));
	}
	
	public void salvar(){
		cadastroEmpresa.salvar(empresaEdicao);
		consultar();
		
		messages.info("Empresa salva com sucesso!");
		
		/*pega as instacias com esse id e dá um update caso seja bem sucedida*/
		RequestContext.getCurrentInstance().update(
				Arrays.asList("frm:msgs", "frm:empresas-table"));
	}
}