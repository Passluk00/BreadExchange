import {ChangeDetectorRef, Component, HostListener, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {
  faArrowRightFromBracket,
  faMagnifyingGlass,
  faShop,
  faTriangleExclamation,
  faUser
} from "@fortawesome/free-solid-svg-icons";
import {isPlatformBrowser, NgClass, NgForOf, NgIf} from "@angular/common";
import {AuthenticationService} from "../../../services/services/authentication.service";
import {UserControllerService} from "../../../services/services/user-controller.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FrontEndControllerService} from "../../../services/services/front-end-controller.service";
import {BakeryNavBarResponse} from "../../../services/models/bakery-nav-bar-response";



@Component({
  selector: 'app-navbar',
  imports: [
    FaIconComponent,
    RouterLink,
    NgIf,
    NgClass,
    NgForOf,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './navbar.component.html',
  standalone: true,
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{


  constructor(
    private router:Router,
    private authService: AuthenticationService,
    private userService: UserControllerService,
    private cdr: ChangeDetectorRef,
    private frontEndService: FrontEndControllerService,

  @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }


  protected readonly faUser = faUser;
  protected readonly faShop = faShop;
  protected readonly faMagnifyingGlass = faMagnifyingGlass;
  protected readonly faTriangleExclamation = faTriangleExclamation;
  protected readonly faArrowRightFromBracket = faArrowRightFromBracket;

  isLogged: boolean = false;
  userData:any;
  isDropdownOpen = false;
  isAdmin: boolean = false;
  results: BakeryNavBarResponse[] = []
  searchValue: string = ""
  isOwner: boolean = false
  idBacOwner: number = 0


  toggleDropdown(){
    this.isDropdownOpen = !this.isDropdownOpen;
  }


  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent){
    const target = event.target as HTMLElement;
    const clickedInside = target.closest('.relative');
    if(!clickedInside){
      this.isDropdownOpen = false;
    }
  }


  ngOnInit() {
    this.checkLoginStatus();
    this.checkOwner()
    this.cdr.detectChanges();
  }


  onSearch(){

    if(this.searchValue != ""){

      this.frontEndService.searchBakery({
        name: this.searchValue
      }).subscribe({
        next: (res) => {
          this.results = res
        },
        error: (err) => {
          console.error("Errore Search Bakery "+ err)
        }
      })

    }
  }

  controllo(){
    this.userService.isAdmin().subscribe({
      next:(res) => {
        this.isAdmin = res
      },
      error:() => {
        console.error("fallito")
      }
    })
  }

  fetchMod(){
    this.userService.getCurrentUser().subscribe({
      next: (data) => {
        this.userData = data;
      },
      error: (err) =>{
        console.log("errore: " + err);
      }
    })
  }

  checkLoginStatus(): void{
    if(isPlatformBrowser(this.platformId)) {
      const toc = this.getToken()

      if(toc) {
        this.authService.check({
          token: toc
        }).subscribe({
          next: (res) => {
            this.isLogged = res;
            this.fetchMod()
            this.controllo()
            this.cdr.detectChanges();

          },
          error: (err) => {
            console.error("Errore nel verificare se sei loggato: " + err)
          }
        })
      }
    }
  }

  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){
      return localStorage.getItem("token")
    }
    return null
  }

  logout():void {
    if(typeof localStorage.getItem('token') !== 'undefined'&& typeof window !=='undefined'){
      localStorage.removeItem("token")
      window.location.reload()
    }
  }

  goToUserPage() {
    this.router.navigate(['user']);
  }

  goToAdminPage(){
    this.router.navigate(['admin'])
  }

  goToManage(){
    this.router.navigate([`bakery/manage/${this.idBacOwner}`])
  }

  checkOwner(){
    this.userService.checkOwnerOfBakery().subscribe({
      next: (res) => {
        if(res.status != undefined && res.id != undefined){
          this.isOwner = res.status
          this.idBacOwner = res.id
        }
      },
      error: (err) => {
        console.error("non sei un owner id una bakery")
      }
    })
  }



}
