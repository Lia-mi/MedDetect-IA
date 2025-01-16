package com.meddetectai.main;

import java.util.ArrayList;
import java.util.HashMap;

public class GerenciarDiag {

    private HashMap<Paciente, ArrayList<Diagnostico>> resultados = new HashMap<>();

    public void addDiagnostico(Paciente paciente, Diagnostico diagnostico) {
        if (!resultados.containsKey(paciente)) {
            resultados.put(paciente, new ArrayList<>());
        }
        resultados.get(paciente).add(diagnostico);
    }

    public boolean existsPaciente(Paciente paciente) {
        return resultados.containsKey(paciente);
    }

    public void addPaciente(Paciente paciente) {
        if (!existsPaciente(paciente)) {
            resultados.put(paciente, new ArrayList<>()); // Add patient with an empty list of diagnostics
        }
    }

}
