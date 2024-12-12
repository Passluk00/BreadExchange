import { Component } from '@angular/core';
import {FallingBreadComponent} from "../components/falling-bread/falling-bread.component";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FallingBreadComponent,
    NgIf,
    NgForOf,
    FormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email:'', password:''}
  errorMessage: Array<String> = [];


  constructor(
    private router:Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {
  }


  login() {
    this.errorMessage = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({

      next: (res) => {

        var toc = res.token;
        if(toc != undefined) {

          //mette il token nel localStorage
          this.tokenService.token = toc;

        }
        this.router.navigate(['homepage']);
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMessage = err.error.validationErrors;
        } else {
          this.errorMessage.push(err.error.errorMsg);
        }
      }
    });
  }

  register() {
    this.router.navigate(['register']);
  }

  forgotPassword() {
    this.router.navigate(['forgot-password']);
  }









  //TODO fare pagina forgot password   e    Icone non Visibili

}
