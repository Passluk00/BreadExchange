import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AdminControllerService} from "../../../../../../services/services/admin-controller.service";
import {PageResponseBakeryRegisterRequest} from "../../../../../../services/models/page-response-bakery-register-request";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-list-requests',
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './list-requests.component.html',
  standalone: true,
  styleUrl: './list-requests.component.scss'
})
export class ListRequestsComponent implements OnInit{



  constructor(
    private adminService: AdminControllerService,
    private cdr: ChangeDetectorRef
  ) {}


  page= 0;
  size = 10;
  requests: PageResponseBakeryRegisterRequest = {};
  pages: any =[]


  ngOnInit(): void {
    this.findAllRequest()
  }

  findAllRequest(){
    this.adminService.getAllRequest({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (res) => {
        this.requests = res;
        this.pages = Array(this.requests.totalPages).fill(0).map((x,i) =>i )

      },
      error: (err) => {
        console.log("Fetch request Fallito: "+ err)
      }
    })
  }

  accept(id:number){
    this.adminService.enableBakery({
      id:id
    }).subscribe({
      next: () => {
        console.log("Richiesta Accettata con successo")
        this.cdr.detectChanges()
        window.location.reload()
      },
      error: () => {
        console.log("Richesta Accept Fallita")
      }
    })
  }

  deny(id:number){
    this.adminService.rejectRequest({
      id:id
    }).subscribe({
      next: () => {
        console.log("Richiesta Rifiutata con Successo")
        this.cdr.detectChanges()
        window.location.reload()
      },
      error: () => {
        console.log("Richiesta Deny Fallita")
      }
    })
  }

  toSearch: string = "";
  isEmpty = true;

  searchRequestByName(){
    this.requests = {}
    this.adminService.searchByName({
      name: this.toSearch
    }).subscribe({
      next:(res) => {
        this.requests = res
        this.isEmpty = false;
      },
      error:() => {
        this.isEmpty = true;
        console.error("Errore nella ricerca tramite nome")
      }
    })
  }



  goToPreviusPage(){
    this.page --;
    this.findAllRequest()
  }

  goToPage(page: number){
    this.page = page;
    this.findAllRequest()
  }

  goToNextPage(){
    this.page ++
    this.findAllRequest()
  }

  isLastPage(){
    return this.page === this.requests.totalPages as number - 1;
  }




}
