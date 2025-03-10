package imposto.imposto.service;

import imposto.imposto.exception.ResourceNotFoundException;
import imposto.imposto.model.Imposto;
import imposto.imposto.repository.ImpostoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpostoService {

    @Autowired
    private ImpostoRepository impostoRepository;

    public List<Imposto> listarTodos() {
        return impostoRepository.findAll();
    }

    public Imposto obterImpostoPorId(Long id) {
        return impostoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imposto não encontrado com ID: " + id));
    }

    public Imposto criarImposto(Imposto imposto) {
        return impostoRepository.save(imposto);
    }

    public void excluirImposto(Long id) {
        if (!impostoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Imposto não encontrado com ID: " + id);
        }
        impostoRepository.deleteById(id);
    }

    public double calcularImposto(Long id, double valorBase) {
        Imposto imposto = obterImpostoPorId(id);
        return valorBase * (imposto.getAliquota() / 100);
    }
}
