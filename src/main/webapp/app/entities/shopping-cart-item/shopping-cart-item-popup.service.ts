import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ShoppingCartItem } from './shopping-cart-item.model';
import { ShoppingCartItemService } from './shopping-cart-item.service';
@Injectable()
export class ShoppingCartItemPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private shoppingCartItemService: ShoppingCartItemService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.shoppingCartItemService.find(id).subscribe((shoppingCartItem) => {
                this.shoppingCartItemModalRef(component, shoppingCartItem);
            });
        } else {
            return this.shoppingCartItemModalRef(component, new ShoppingCartItem());
        }
    }

    shoppingCartItemModalRef(component: Component, shoppingCartItem: ShoppingCartItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.shoppingCartItem = shoppingCartItem;
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
