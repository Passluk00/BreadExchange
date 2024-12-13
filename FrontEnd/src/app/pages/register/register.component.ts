import { Component } from '@angular/core';
import {FallingBreadComponent} from "../components/falling-bread/falling-bread.component";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {RegistrationRequest} from "../../services/models/registration-request";
import {faUser, faKey, faEnvelope} from "@fortawesome/free-solid-svg-icons"
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'app-register',
  imports: [
    FontAwesomeModule,
    FallingBreadComponent,
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './register.component.html',
  standalone: true,
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  errorMessage: Array<String> = [];
  authRequest: RegistrationRequest = {email: '' ,username: '' ,password: ''}


  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) { }

  register(){

    this.errorMessage = [];
    this.authService.register({
      body: this.authRequest
    }).subscribe({
      next: (res) => {
        this.router.navigate(['activate-account']);     // TODO DA Cambiare con pagina di auth codice
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMessage = err.error.validationErrors;
        }else{
          this.errorMessage.push(err.error.error);
        }
      }
    })
  }



  protected readonly faUser = faUser;
  protected readonly faEnvelope = faEnvelope;
  protected readonly faKey = faKey;

}
