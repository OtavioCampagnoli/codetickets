package br.com.alura.codetickets;

import org.springframework.batch.item.ItemProcessor;

public class ImportacaoProcessor implements ItemProcessor<Importacao, Importacao> {

    @Override
    public Importacao process(Importacao item) throws Exception {
        this.populateTaxaAdministrativa(item);
        return item;
    }

    private void populateTaxaAdministrativa(Importacao item) {
        if (item.getTipoIgresso().equalsIgnoreCase("Pista Premium")) {
            item.setTaxaAdministrativa(20.0);
        } else if (item.getTipoIgresso().equalsIgnoreCase("Camarote")) {
            item.setTaxaAdministrativa(40.0);
        } else if (item.getTipoIgresso().equalsIgnoreCase("Arquibancada")) {
            item.setTaxaAdministrativa(5.0);
        } else if (item.getTipoIgresso().equalsIgnoreCase("Pista")) {
            item.setTaxaAdministrativa(10.0);
        } else if (item.getTipoIgresso().equalsIgnoreCase("VIP")) {
            item.setTaxaAdministrativa(30.0);
        } else {
            throw new IllegalArgumentException("Tipo de ingresso inválido: " + item.getTipoIgresso());
        }
    }
}
