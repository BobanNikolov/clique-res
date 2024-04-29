import {FormGroup, FormControl, Validators, ReactiveFormsModule} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { CustomValidators } from '../../custom-validator';
import { AuthService } from '../../services/auth-service/auth.service';
import { tap } from 'rxjs';
import {Router, RouterLink} from '@angular/router';
import {Role, SignUpRequest} from "../../interfaces";
import {LOCALSTORAGE_TOKEN_KEY} from "../../../app.module";
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatError, MatFormField} from "@angular/material/form-field";

@Component({
  selector: 'app-register',
  templateUrl: './signup.component.html',
  standalone: true,
  imports: [
    MatCardTitle,
    RouterLink,
    MatCard,
    MatCardContent,
    ReactiveFormsModule,
    MatFormField,
    MatError
  ],
  styleUrls: ['./signup.component.scss']
})
export class SignUpComponent {

  signUpForm = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    role: new FormControl(Role.ROLE_MANAGER, [Validators.required])
  },
    // add custom Validators to the form, to make sure that password and passwordConfirm are equal
    { validators: CustomValidators.passwordsMatching }
  )

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  register() {
    if (!this.signUpForm.valid) {
      return;
    }
    this.authService.register(this.signUpForm.value as SignUpRequest).pipe(
      // If registration was successfull, then navigate to login route
      tap(() => this.router.navigate(['../signin']))
    ).subscribe();
  }

}
