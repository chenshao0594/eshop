import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShoppingCartItemDetailComponent } from '../../../../../../main/webapp/app/entities/shopping-cart-item/shopping-cart-item-detail.component';
import { ShoppingCartItemService } from '../../../../../../main/webapp/app/entities/shopping-cart-item/shopping-cart-item.service';
import { ShoppingCartItem } from '../../../../../../main/webapp/app/entities/shopping-cart-item/shopping-cart-item.model';

describe('Component Tests', () => {

    describe('ShoppingCartItem Management Detail Component', () => {
        let comp: ShoppingCartItemDetailComponent;
        let fixture: ComponentFixture<ShoppingCartItemDetailComponent>;
        let service: ShoppingCartItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ShoppingCartItemDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShoppingCartItemService,
                    EventManager
                ]
            }).overrideComponent(ShoppingCartItemDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingCartItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingCartItemService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShoppingCartItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shoppingCartItem).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
