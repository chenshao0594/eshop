import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ShoppingCart } from './shopping-cart.model';
import { ShoppingCartPopupService } from './shopping-cart-popup.service';
import { ShoppingCartService } from './shopping-cart.service';

@Component({
    selector: 'jhi-shopping-cart-dialog',
    templateUrl: './shopping-cart-dialog.component.html'
})
export class ShoppingCartDialogComponent implements OnInit {

    shoppingCart: ShoppingCart;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private shoppingCartService: ShoppingCartService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shoppingCart']);
        this.shoppingCart = new ShoppingCart();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shoppingCart.id !== undefined) {
            this.shoppingCartService.update(this.shoppingCart)
                .subscribe((res: ShoppingCart) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.shoppingCartService.create(this.shoppingCart)
                .subscribe((res: ShoppingCart) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ShoppingCart) {
        this.eventManager.broadcast({ name: 'shoppingCartListModification', content: 'OK'});
        this.isSaving = false;
        this.shoppingCart = result;
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
}

@Component({
    selector: 'jhi-shopping-cart-popup',
    template: ''
})
export class ShoppingCartPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingCartPopupService: ShoppingCartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.shoppingCartPopupService
                    .open(ShoppingCartDialogComponent, params['id']);
            } else {
                this.modalRef = this.shoppingCartPopupService
                    .open(ShoppingCartDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
