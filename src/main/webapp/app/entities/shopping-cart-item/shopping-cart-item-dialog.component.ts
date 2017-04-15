import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ShoppingCartItem } from './shopping-cart-item.model';
import { ShoppingCartItemPopupService } from './shopping-cart-item-popup.service';
import { ShoppingCartItemService } from './shopping-cart-item.service';
import { ShoppingCart, ShoppingCartService } from '../shopping-cart';

@Component({
    selector: 'jhi-shopping-cart-item-dialog',
    templateUrl: './shopping-cart-item-dialog.component.html'
})
export class ShoppingCartItemDialogComponent implements OnInit {

    shoppingCartItem: ShoppingCartItem;
    authorities: any[];
    isSaving: boolean;

    shoppingcarts: ShoppingCart[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private shoppingCartItemService: ShoppingCartItemService,
        private shoppingCartService: ShoppingCartService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shoppingCartItem']);
        this.shoppingCartItem = new ShoppingCartItem();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.shoppingCartService.query().subscribe(
            (res: Response) => { this.shoppingcarts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shoppingCartItem.id !== undefined) {
            this.shoppingCartItemService.update(this.shoppingCartItem)
                .subscribe((res: ShoppingCartItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.shoppingCartItemService.create(this.shoppingCartItem)
                .subscribe((res: ShoppingCartItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ShoppingCartItem) {
        this.eventManager.broadcast({ name: 'shoppingCartItemListModification', content: 'OK'});
        this.isSaving = false;
        this.shoppingCartItem = result;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackShoppingCartById(index: number, item: ShoppingCart) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-shopping-cart-item-popup',
    template: ''
})
export class ShoppingCartItemPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingCartItemPopupService: ShoppingCartItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.shoppingCartItemPopupService
                    .open(ShoppingCartItemDialogComponent, params['id']);
            } else {
                this.modalRef = this.shoppingCartItemPopupService
                    .open(ShoppingCartItemDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
