import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShoppingCartAttributeItemDetailComponent } from '../../../../../../main/webapp/app/entities/shopping-cart-attribute-item/shopping-cart-attribute-item-detail.component';
import { ShoppingCartAttributeItemService } from '../../../../../../main/webapp/app/entities/shopping-cart-attribute-item/shopping-cart-attribute-item.service';
import { ShoppingCartAttributeItem } from '../../../../../../main/webapp/app/entities/shopping-cart-attribute-item/shopping-cart-attribute-item.model';

describe('Component Tests', () => {

    describe('ShoppingCartAttributeItem Management Detail Component', () => {
        let comp: ShoppingCartAttributeItemDetailComponent;
        let fixture: ComponentFixture<ShoppingCartAttributeItemDetailComponent>;
        let service: ShoppingCartAttributeItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ShoppingCartAttributeItemDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShoppingCartAttributeItemService,
                    EventManager
                ]
            }).overrideComponent(ShoppingCartAttributeItemDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingCartAttributeItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingCartAttributeItemService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShoppingCartAttributeItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shoppingCartAttributeItem).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
