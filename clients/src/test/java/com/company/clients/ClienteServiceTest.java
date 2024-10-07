package com.company.clients;

import com.company.clients.model.Cliente;
import com.company.clients.repository.ClienteRepository;
import com.company.clients.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void testCrearCliente() {

        Random random = new Random();
        int randomInt = random.nextInt(99);
        String randomIdentificacion = "ID1234" + String.valueOf(randomInt);

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion(randomIdentificacion);
        cliente.setDireccion("Calle 1");
        cliente.setTelefono("555-1234");
        cliente.setContrasena("password1");
        cliente.setEstado("Activo");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteGuardado = clienteService.createCliente(cliente);

        assertNotNull(clienteGuardado);
        assertEquals("Juan Pérez", clienteGuardado.getNombre());
        assertEquals("Masculino", clienteGuardado.getGenero());
        assertEquals(30, clienteGuardado.getEdad());
    }

    @Test
    void testObtenerClientePorId() {

        Cliente cliente = new Cliente();
        cliente.setClienteId(6688L);
        cliente.setNombre("Maria López");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Optional<Cliente> clienteObtenido = clienteService.getClienteById(1L);

        assertTrue(clienteObtenido.isPresent());
        assertEquals("Maria López", clienteObtenido.get().getNombre());
    }
}