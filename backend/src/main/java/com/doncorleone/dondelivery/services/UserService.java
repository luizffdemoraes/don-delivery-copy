package com.doncorleone.dondelivery.services;

import com.doncorleone.dondelivery.dto.RoleDTO;
import com.doncorleone.dondelivery.dto.UserDTO;
import com.doncorleone.dondelivery.dto.UserInsertDTO;
import com.doncorleone.dondelivery.entities.Role;
import com.doncorleone.dondelivery.entities.User;
import com.doncorleone.dondelivery.repositories.RoleRepository;
import com.doncorleone.dondelivery.repositories.UserRepository;
import com.doncorleone.dondelivery.services.exceptions.DatabaseException;
import com.doncorleone.dondelivery.services.exceptions.ObjectNotFoundException;
import com.doncorleone.dondelivery.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //usado pra encodar a senha que vem no UserInsertDTO. Criado no AppConfig


    public User find(Long id)	{
        return repository.findById(id).orElseThrow( () -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + User.class.getName() ) );
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        Page<User> list = repository.findAll(pageRequest);

        // to transform a List<User> to List<UserDTO>
        return list.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = repository.findById(id); // findById returns an Optional.
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found")); // to get as User the Optional object
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPasswords(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return new UserDTO(entity);

    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User entity = repository.getOne(id); // .getOne() dont touch the DB. Instantiates a provisory object
            // with that ID. Just touch the DB when to save
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found:" + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found.");
        } catch (DataIntegrityViolationException e) { // integrity. in case that there are products with the category,
            // cant be deleted
            throw new DatabaseException("Integrity violaton.");
        }
    }

    // used to transform and DTO into Entity, avoiding to have to do entity.setXXX
    // every time. Obs: if one of the fields are null on update request, they will be saved like that!!!
    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.getRoles().clear();

        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getOne(roleDto.getId());
            entity.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Email not found.");
        }

        return user;
    }
}