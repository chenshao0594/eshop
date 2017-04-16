import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ShoppingCart } from './shopping-cart.model';
import { ShoppingCartService } from './shopping-cart.service';

@Component({
    selector: 'jhi-shopping-cart-detail',
    templateUrl: './shopping-cart-detail.component.html'
})
export class ShoppingCartDetailComponent implements OnInit, OnDestroy {

    shoppingCart: ShoppingCart;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private shoppingCartService: ShoppingCartService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['shoppingCart']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShoppingCarts();
    }

    load(id) {
        this.shoppingCartService.find(id).subscribe((shoppingCart) => {
            this.shoppingCart = shoppingCart;
        });
    }
    previousState() {
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
        this.eventManager.broadcast({ name: 'shoppingCartModification', content: 'OK'});
        this.isSaving = false;
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShoppingCarts() {
        this.eventSubscriber = this.eventManager.subscribe('shoppingCartListModification', (response) => this.load(this.shoppingCart.id));
    }
}
