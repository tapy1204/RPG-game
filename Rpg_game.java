package sec01;

import java.util.Scanner;

public class Rpg_game {
	static int hero_level, hero_power, hero_hp, hero_defense, hero_mp, hero_experience, hero_money;
	static int monster_hp, monster_defense, monster_power, monster_mp, monster_level, monster_experience, monster_money;
	static String hero_name, monster_name;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		hero_level = 1;
		hero_power = 15;
		hero_hp = 80;
		hero_defense = 25;
		hero_mp = 0;
		hero_experience = 0;
		hero_money = 0;

		// 영웅 이름 입력 받은 후 게임 입장
		System.out.print("영웅의 이름을 입력하세요. : ");
		hero_name = in.next();
		System.out.println("이름이 입력되었습니다.");
		System.out.println("게임에 입장하였습니다.");

		System.out.println("***********************");

		// 영웅 정보 출력
		heroInfo();

		// 장소입장 선택 무한루프
		System.out.println("***********************");
		while (true) {
			System.out.print("1. 사냥터\n2. 포션 상점\n");
			System.out.printf("입장 할 장소를 입력하세요. : ");
			int place = in.nextInt();

			// 장소 place 가 1일 경우 사냥터 입
			if (place == 1) {
				System.out.println("사냥터에 입장하였습니다.");

				// 레벨이 1일 경우 너구리만 있는 사냥터 입장
				if (hero_level < 2) {
					System.out.println("1. 너구리");
					System.out.print("전투할 상대를 입력하세요. : ");
					int fight_num = in.nextInt();

					// 정적메서드로 전투 진행
					fight(fight_num, in);

					// 전투결과 영웅 사망 시 부활
					if (hero_hp == 0) {
						System.out.println("영웅이 사망했습니다, 부활합니다.");
						System.out.println("***********************");
						hero_revive();
						System.out.println("***********************");
					}

					// 전투결과 몬스터 사망 시 경험치, 돈 증가 후 영웅 정보 출력
					if (monster_hp <= 0) {
						monster_death();

						// 전투 후 항상 레벨업 조건 부합여부 확인 부합 시 레벨업
						if (hero_experience >= hero_level * 80) {
							heroLevelup();
						}
						System.out.println("***********************");
						heroInfo();
						System.out.println("***********************");
					}

				}

				// 레벨 2 이상일 경우 살쾡이와 너구리가 있는 사냥터 입장
				else {
					System.out.print("1. 너구리\n2. 살쾡이\n");
					System.out.print("전투할 상대를 입력하세요. : ");

					// 위 전투 메커니즘과 동일
					int fight_num = in.nextInt();

					fight(fight_num, in);

					if (hero_hp == 0) {
						System.out.println("영웅이 사망했습니다, 부활합니다.");
						System.out.println("***********************");
						hero_revive();
						System.out.println("***********************");
					}

					if (monster_hp <= 0) {
						monster_death();
						System.out.println("***********************");
						heroInfo();
						System.out.println("***********************");
					}

					if (hero_experience >= hero_level * 80) {
						heroLevelup();
					}

				}

			}

			// 장소 place 가 2일 경우 포션 상점 입장
			else if (place == 2) {
				System.out.println("포션 상점에 입장하였습니다.");

				// 포션 정보 출력
				potion_info();

				// 구매할 포션 번호 입력
				System.out.print("원하시는 물건을 입력하세요. : ");
				int potion_num = in.nextInt();

				// 포션 구매 진행
				potionStore_show(hero_money, potion_num);
				System.out.println("***********************");

			}
		}

	}

	// 영웅의 정보를 출력
	static void heroInfo() {
		System.out.println("현재 " + hero_name + "의 레벨 : " + hero_level);
		System.out.println("현재 " + hero_name + "의 힘 : " + hero_power);
		System.out.println("현재 " + hero_name + "의 방어력 : " + hero_defense);
		System.out.println("현재 " + hero_name + "의 HP : " + hero_hp);
		System.out.println("현재 " + hero_name + "의 MP : " + hero_mp);
		System.out.println("현재 " + hero_name + "의 경험치 : " + hero_experience);
		System.out.println("현재 " + hero_name + "의 돈 : " + hero_money);
	}

	// 영웅이 공격 시 넣는 순수데미지 출력
	static int hero_attack() {

		int sum = hero_level * 10 + hero_power * 30;

		return sum;
	}

	// 영웅이 공격 받을 시 받는 데미지 출력
	static void hero_attacked(int sum) {
		if (hero_defense >= sum) {

		} else {
			hero_hp = hero_hp + hero_defense - sum;
		}
	}

	// 몬스터가 공격 시 넣는 순수데미지 출력
	static int monster_attack() {

		int sum = monster_power;

		return sum;
	}

	// 몬스터가 공격 받을 시 받는 데미지 출력
	static void monster_attacked(int sum) {
		if (monster_defense >= sum) {

		} else {
			monster_hp = monster_hp + monster_defense - sum;
		}
	}

	// 레벨 2에 사용 가능 한 스킬 쓰러스트의 순수 데미지 산정
	static int hero_thrust() {
		int sum = hero_level * 10 + hero_power * 42;

		return sum;
	}

	// 전투 진행 메서드
	static void fight(int n, Scanner in) {

		// 각 몬스터와의 전투 선택시 몬스터 스테이터스 설정
		if (n == 1) {
			monster_name = "너구리";
			monster_hp = 100;
			monster_mp = 0;
			monster_level = 1;
			monster_power = 20;
			monster_defense = 5;
			monster_money = 10;
			monster_experience = 10;
		}
		if (n == 2) {
			monster_name = "살쾡이";
			monster_hp = 2000;
			monster_mp = 0;
			monster_level = 5;
			monster_power = 100;
			monster_defense = 20;
			monster_money = 30;
			monster_experience = 50;
		}

		System.out.println(monster_name + "와 전투를 시작합니다.");

		while (hero_level == 1) {
			// 영웅, 몬스터 각 공격 시 넣는 순수데미지를 저장하는 변수
			int sum;

			// 영웅의 공격
			System.out.println(hero_name + "의 공격입니다.");
			sum = hero_attack();
			monster_attacked(sum); // 영웅의 순수데미지를 입력받아 실제로 몬스터가 받는 데미지로 monster_hp 감소시키는 메서드
			System.out.println("데미지는 " + hero_attack() + " 입니다.");

			// 몬스터 사망 확인
			if (monster_hp <= 0) {
				System.out.println(monster_name + "가 죽었습니다.");
				break;
			}

			// 몬스터의 공격 영웅 공격 시 매커니즘과 동일
			System.out.println(monster_name + "의 공격입니다.");
			sum = monster_attack();
			hero_attacked(monster_attack());
			System.out.println("데미지는 " + monster_attack() + " 입니다.");

			// 영웅 사망 확인
			if (hero_hp <= 0) {
				hero_hp = 0;
				break;
			}
		}
		// 영웅 레벨 2로 레벨 업시 새로운 스킬 쓰러스트 사용 가능
		while (hero_level >= 2) {
			// 영웅, 몬스터 각 공격 시 넣는 순수데미지를 저장하는 변수
			int sum;

			// 영웅의 공격
			System.out.println(hero_name + "의 공격입니다.");

			// 공격 스킬 선택
			System.out.print("1. 일반 공격\n 2. 쓰러스트\n");
			System.out.print("공격 번호를 입력하세요.");
			int skill_num = in.nextInt();

			// 공격 별 데미지 산정 후 공격
			if (skill_num == 1) {

				// 일반 공격 시 기존 hero_attack와 데미지 동일
				sum = hero_attack();
				monster_attacked(sum);
				System.out.println("데미지는 " + hero_attack() + " 입니다.");

				// 몬스터 사망 확인
				if (monster_hp <= 0) {
					System.out.println(monster_name + "가 죽었습니다.");
					break;
				}

				// 몬스터의 공격 영웅 공격 시 매커니즘과 동일
				System.out.println(monster_name + "의 공격입니다.");
				sum = monster_attack();
				hero_attacked(monster_attack());
				System.out.println("데미지는 " + monster_attack() + " 입니다.");

				// 영웅 사망 확인
				if (hero_hp <= 0) {
					hero_hp = 0;
					break;
				}

			} else if (skill_num == 2) {

				// 쓰러스트 공격 시 새로운 공격 매커니즘 hero_thrust() 사용
				sum = hero_thrust();
				monster_attacked(sum);
				System.out.println("데미지는 " + hero_thrust() + " 입니다.");

				// 몬스터 사망 확인
				if (monster_hp <= 0) {
					System.out.println(monster_name + "가 죽었습니다.");
					break;
				}

				// 몬스터의 공격 영웅 공격 시 매커니즘과 동일
				System.out.println(monster_name + "의 공격입니다.");
				sum = monster_attack();
				hero_attacked(monster_attack());
				System.out.println("데미지는 " + monster_attack() + " 입니다.");

				// 영웅 사망 확인
				if (hero_hp <= 0) {
					hero_hp = 0;
					break;
				}
			}

		}

	}

	// 몬스터 사망 시 monster_money monster_experience만큼 영웅의 경험치, 돈 증가
	static void monster_death() {
		hero_experience += monster_experience;
		hero_money += monster_money;
	}

	// 영웅의 레벨업 매커니즘 작동
	static void heroLevelup() {
		hero_experience = 0;
		hero_level += 1;
		hero_money += hero_level * 50;
		System.out.println(hero_name + "의 레벨이 " + hero_level + "이 되었습니다.");
		System.out.println("레벨업 기념으로 돈이 " + hero_level * 50 + "원 증가하여 " + hero_money + "원이 되었습니다.");
	}

	// 영웅 부활 매커니즘 부활 후 hero_hp = 1인 영웅의 스테이터스 출력
	static void hero_revive() {
		System.out.println("현재 Hero 의 이름 : " + hero_name);
		hero_power = 15;
		hero_hp = 1;
		hero_defense = 25;
		hero_mp = 0;
		heroInfo();
	}

	// 포션 상점 상품 목록 출력
	static void potion_info() {
		System.out.println("1. 힘 증강 포션 (30원)");
		System.out.println("2. 방어력 증강 포션(30원)");
		System.out.println("3. 경험치 증강 포션 (100원)");
		System.out.println("4. HP 증강 포션 (10원)");
		System.out.println("5. MP 증강 포션 (10원)");
	}

	// 영웅의 남은 돈을 반환하는 포션 상점의 구매 메소드
	static int potionStore_show(int money, int num) {
		// 포션 가격 변수 선언
		int price = 0;

		// 구매 선택한 포션 번호에 따른 포션 가격 price 설정
		switch (num) {
		case 1, 2 -> price = 30;
		case 3 -> price = 100;
		case 4, 5 -> price = 10;
		}

		System.out.println("포션 상점에서 물건을 구매 시도하는 중입니다.");

		// 입력받은 현재 돈과 price 비교 후 구매 가능 여부 확인
		if (money >= price) {

			// 구매 가능할 경우 구매한 포션 번호에 따라 스테이터스 증가
			switch (num) {
			case 1 -> hero_power += 3;
			case 2 -> hero_defense += 3;
			case 3 -> hero_experience += 50;
			case 4 -> hero_hp += 50;
			case 5 -> hero_mp += 50;
			}

			// 구매 후 영웅의 돈 감소
			hero_money = money - price;

			// 남은 금액 출력
			System.out.println("구입이 완료되었습니다.");
			System.out.println(hero_money + "원 남았습니다.");
			System.out.println("***********************");
			heroInfo();
		} else {

			// 금액 부족 시 안내 출력
			System.out.println("금액이 부족합니다.");
		}

		return hero_money;
	}

}
