import { LOCALSTORAGE_TOKEN_KEY } from './../../../app.module';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, of, switchMap, tap } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SignInRequest, SignUpRequest, JwtAuthenticationToken } from '../../interfaces';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private jwtService: JwtHelperService
  ) { }


  login(signInRequest: SignInRequest): Observable<JwtAuthenticationToken> {

    return this.http.post<JwtAuthenticationToken>('/api/v1/auth/signin', signInRequest).pipe(
    tap((res: JwtAuthenticationToken) => localStorage.setItem(LOCALSTORAGE_TOKEN_KEY, res.token)),
    tap(() => this.snackbar.open('Login Successfull', 'Close', {
     duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'
    }))
    );
  }


  register(signUpRequest: SignUpRequest): Observable<JwtAuthenticationToken> {

    return this.http.post<JwtAuthenticationToken>('/api/v1/auth/signup', signUpRequest).pipe(
    tap((res: JwtAuthenticationToken) => this.snackbar.open(`User created successfully`, 'Close', {
     duration: 2000, horizontalPosition: 'right', verticalPosition: 'top'
    }))
    )
  }

  /*
   Get the user fromt the token payload
   */
  getLoggedInUser() {
    const decodedToken = this.jwtService.decodeToken();
    return decodedToken.user;
  }
}