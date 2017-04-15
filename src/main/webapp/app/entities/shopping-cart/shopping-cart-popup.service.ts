import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ShoppingCart } from './shopping-cart.model';
import { ShoppingCartService } from './shopping-cart.service';
@Injectable()
export class ShoppingCartPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private shoppingCartService: ShoppingCartService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.shoppingCartService.find(id).subscribe((shoppingCart) => {
                this.shoppingCartModalRef(component, shoppingCart);
            });
        } else {
            return this.shoppingCartModalRef(component, new ShoppingCart());
        }
    }

    shoppingCartModalRef(component: Component, shoppingCart: ShoppingCart): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.shoppingCart = shoppingCart;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
