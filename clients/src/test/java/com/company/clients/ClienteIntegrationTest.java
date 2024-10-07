package com.company.clients;

import com.company.clients.model.Cliente;
import com.company.clients.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testCrearYObtenerCliente() throws Exception {

        Random random = new Random();
        int randomInt = random.nextInt(9999);
        String randomIdentificacion = "IDEN" + String.valueOf(randomInt);
        String randomNombre = "Juan Pérez " + String.valueOf(randomInt);

        String clienteJson = """
                {
                    "nombre": \"""" + randomNombre + """ 
                ",
                "genero": "Masculino",
                "edad": 30,
                "identificacion": \"""" + randomIdentificacion + """
                ",
                        "direccion": "Calle Falsa 123",
                        "telefono": "555-1234",
                        "clienteId": \"""" + randomInt + """
                ",
                        "contrasena": "password1",
                        "estado": "Activo"
                    }
                """;


        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.identificacion").value(randomIdentificacion));

        Optional<Cliente> clienteGuardado = clienteRepository.findByIdentificacion(randomIdentificacion);
        assert clienteGuardado.isEmpty() || clienteGuardado.get().getNombre().equals(randomNombre);

        mockMvc.perform(get("/clientes/identificacion/{identificacion}", randomIdentificacion))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value(randomNombre))
                .andExpect(jsonPath("$.identificacion").value(randomIdentificacion));
    }
}
