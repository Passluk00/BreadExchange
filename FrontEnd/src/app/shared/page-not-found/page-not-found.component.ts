import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BakeryControllerService} from "../../services/services/bakery-controller.service";
import {ItemRequest} from "../../services/models/item-request";
import {ListCategoryFrontEnd} from "../../services/models/list-category-front-end";
import {FrontEndControllerService} from "../../services/services/front-end-controller.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-page-not-found',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    NgForOf
  ],
  templateUrl: './page-not-found.component.html',
  standalone: true,
  styleUrl: './page-not-found.component.scss'
})
export class PageNotFoundComponent implements OnInit{

  constructor(
    private service: BakeryControllerService,
    private frontEndService: FrontEndControllerService,
    private cdr: ChangeDetectorRef,
  ) {
  }

  owner:number = 1    // da prendere dinamicamente con pssaggio da url
  catName: string = ""
  categorySelected: number = 0;
  categorySelectedDel: number | undefined
  itemreq: ItemRequest = {
    name:"",
    description:"",
    price:0
  }
  itemMod: ItemRequest = {
    name:"",
    description:"",
    price:0
  }
  toUpload:any;
  data:ListCategoryFrontEnd = {}

  ngOnInit(): void {
    this.getCategorys()
  }


  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.toUpload = input.files[0];
      console.log('File selezionato:', this.toUpload);
    }
  }

  getCategorys(){
    this.frontEndService.getAllCategory({
      owner: this.owner                 // da cambiare dinamicamente
    }).subscribe({
      next:(res) => {
        this.data = res
      },
      error:(err) => {
        console.error("errore fetche dati categorie"+ err)
      }
    })
  }


  sendReq(){
    this.service.newItem({
      idBac:1,                          // preso automaticamente da path
      idCat:this.categorySelected,
      body:{
        file:this.toUpload,
        request:this.itemreq
      }
    }).subscribe({
      next:() => {
        console.error("invio dati avvenuto")
        this.cdr.detectChanges()
      },
      error: (err) => {
        console.error("invio dati fallito"+ err)
      }
    })
  }


  newCategory(){
    this.service.newCategory({
      bakeryId:1,                         // preso automaticamente da path
      nomeCat: this.catName
    }).subscribe({
      next:() => {
        console.error("invio dati avvenuto")
        this.cdr.detectChanges()
        window.location.reload()
      },
      error:() => {
        console.error("invio dati fallito")
      }
    })
  }


  modItem(){
    this.service.updateItem({
      idBac: 1,                         // preso dinameicamente da path
      idItem: 1,                        // preso al momento di click tasto modifica
      body: this.itemMod

    }).subscribe({
      next:(res) => {
        console.error("Modifica dati avvenuto")
        this.cdr.detectChanges()
      },
      error:() => {
        console.error("Modifica dati Fallito ")
      }
    })
  }


  delItem(){
    this.service.delItem({
      idItem:1,                           // preso al click
      idCat:1,                            // preso al click
      idBac:1                             // preso dinamicamente da path
    }).subscribe({
      next:(res) => {
        console.error("del avvenuto")
        this.cdr.detectChanges()
      },
      error:(err) => {
        console.error("del fallita")
      }
    })
  }


  delCat(){
    if(this.categorySelectedDel) {
      this.service.delCategory({
        idCat: this.categorySelectedDel
      }).subscribe({
        next: () => {
          console.error("del cat")
          this.cdr.detectChanges()
          window.location.reload()
        },
        error: () => {
          console.error("del cat fallito")
        }
      })
    }
  }


}
