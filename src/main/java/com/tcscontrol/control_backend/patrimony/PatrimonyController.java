package com.tcscontrol.control_backend.patrimony;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.patrimony.model.PatrimonyDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping("api/patrimonio")
@AllArgsConstructor
public class PatrimonyController {

	private final PatrimonyService patrimonyService;

	@GetMapping
	public List<PatrimonyDTO> list() {
		return patrimonyService.list();
	}

	@GetMapping("/{id}")
	public PatrimonyDTO findById(@PathVariable @NotNull @Positive Long id) {
		return patrimonyService.findById(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PatrimonyDTO create(@RequestBody @Valid PatrimonyDTO patrimonyDTO) {
		return patrimonyService.criarPatrimonio(patrimonyDTO);
	}

	@PatchMapping("/{id}")
	public PatrimonyDTO update(@PathVariable Long id, @RequestBody @Valid PatrimonyDTO patrimonyDTO) {
		return patrimonyService.update(id, patrimonyDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable @NotNull @Positive Long id) {
		patrimonyService.delete(id);
	}

}
