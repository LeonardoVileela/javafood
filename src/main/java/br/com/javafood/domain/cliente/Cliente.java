package br.com.javafood.domain.cliente;

import br.com.javafood.domain.restaurante.ItemCardapio;
import br.com.javafood.domain.usuario.Usuario;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Cliente extends Usuario {

    @NotBlank(message = "O CPF não pode ser vazio")
    @Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inválido")
    @Column(length = 11, nullable = false) //tamanho do campo no banco
    private String cpf;

    @NotBlank(message = "O CPF não pode ser vazio")
    @Pattern(regexp = "[0-9]{8}", message = "O CEP possui formato inválido") //validação, aceita somente números de 0 a 9 e no maximo 8 digitos
    @Column(length = 8) //tamanho do campo no banco
    private String cep;

    @Transient
    private Map<ItemCardapio, Integer> carrinho = new HashMap<>();

    public void addItemCarrinho(ItemCardapio itemCardapio){
        System.out.println(itemCardapio.getId());
        this.carrinho.put(itemCardapio, 1);
        System.out.println(this.carrinho);
        System.out.println();
    }

    public void esvaziarCarrinho(){
        carrinho.clear();
    }

    public Map<ItemCardapio, Integer> getItemsCarrinho(){
        return this.carrinho;
    }

    public boolean contain(ItemCardapio itemCardapio){

        for (var car : this.carrinho.entrySet()) {
            if (car.getKey().getId() == itemCardapio.getId()){
                return true;
            }
        }

        return false;

    }

    public void addExisting(int id){
        for (var car : this.carrinho.entrySet()) {
            if (car.getKey().getId() == id){
                car.setValue(car.getValue() + 1);
            }
        }

    }

}
